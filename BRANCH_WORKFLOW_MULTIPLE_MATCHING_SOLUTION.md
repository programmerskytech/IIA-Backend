# Branch Workflow Multiple Matching - Solution & Strategy

## Problem Statement

When an indent/tender matches multiple workflow branches, which branch should be selected? How should approval routing work?

### Example Scenario
**Branch 23 (Amount-based)**:
- Condition: Amount between 50,000 - 100,000
- Approvers: Reporting Officer → Administrative Officer

**Branch 24 (Location-based)**:
- Condition: Location = BANGALORE
- Approvers: Reporting Officer → Store Purchase Officer

**Indent Created**:
- Amount: 75,000
- Location: BANGALORE

**Result**: BOTH branches match!

## Current System Behavior

The current implementation uses **First Match by Display Order**:
- Branches are evaluated in order of `display_order` field
- The first branch that matches all its conditions is selected
- If Branch 23 has `display_order = 1` and Branch 24 has `display_order = 2`, then Branch 23 will be selected

## Solutions

### Solution 1: Priority-Based Selection (RECOMMENDED - CURRENT IMPLEMENTATION)

**How it works**:
- Admin sets `display_order` as priority (1 = highest priority)
- System selects first matching branch by display_order
- Clear, predictable, admin-controlled

**Advantages**:
- Simple to understand and configure
- Admin has full control
- No complex matching logic needed

**Usage Guidelines for Admin**:
1. Assign lower `display_order` to more specific/restrictive branches
2. Assign higher `display_order` to broader branches
3. Example priority order:
   - Order 1: Project-specific + Amount range + Location (most specific)
   - Order 2: Amount range + Location
   - Order 3: Amount range only
   - Order 4: Location only
   - Order 5: Default branch (no conditions)

### Solution 2: Most Restrictive Match

**How it works**:
- Count number of non-null conditions in each matching branch
- Select branch with most conditions (most restrictive)
- If tie, use display_order as tiebreaker

**Example**:
- Branch A: Amount + Location (2 conditions) → Selected
- Branch B: Location only (1 condition) → Not selected

### Solution 3: Explicit Condition Precedence

**How it works**:
- Define a precedence order: Project > Amount > Location > Category
- When multiple branches match, select based on highest precedence condition

### Solution 4: Merge Approvers from All Matching Branches

**How it works**:
- Find all matching branches
- Combine approvers from all branches (removing duplicates by role)
- Route through combined approval chain

**Note**: This is COMPLEX and can create unpredictable approval paths

## Recommended Best Practices

### 1. Mutually Exclusive Branches (BEST APPROACH)
Design branches so they don't overlap:

**Good Example**:
- Branch 1: Amount < 50,000
- Branch 2: Amount 50,000 - 100,000
- Branch 3: Amount > 100,000

**Bad Example** (can overlap):
- Branch 1: Amount 50,000 - 100,000
- Branch 2: Location = BANGALORE

### 2. Use Compound Conditions
If you need both amount and location-based routing, create branches with BOTH conditions:

**Better Approach**:
- Branch 1: Amount 50,000-100,000 AND Location = BANGALORE → Approvers A, B, C
- Branch 2: Amount 50,000-100,000 AND Location = MUMBAI → Approvers A, D, E
- Branch 3: Amount > 100,000 AND Location = BANGALORE → Approvers A, F, G
- Branch 4: Amount > 100,000 AND Location = MUMBAI → Approvers A, H, I

### 3. Default Fallback Branch
Always create a default branch with no conditions as the last display_order:
- This catches any requests that don't match specific branches
- Ensures workflow always has a path

## Current Implementation Status

**File**: `BranchWorkflowServiceImpl.java`
**Method**: `findMatchingBranch()`

```java
public WorkflowBranchMaster findMatchingBranch(Integer workflowId, Map<String, Object> conditions) {
    List<WorkflowBranchMaster> branches = branchRepository
            .findByWorkflowIdAndIsActiveTrue(workflowId)
            .stream()
            .sorted(Comparator.comparing(WorkflowBranchMaster::getDisplayOrder))
            .collect(Collectors.toList());

    // Returns FIRST matching branch by display order
    for (WorkflowBranchMaster branch : branches) {
        if (matchesBranchCondition(branch, conditions)) {
            return branch;
        }
    }

    return null; // No match found
}
```

## How to Configure in Admin Panel

1. **Create Specific Branches First** (Low display_order numbers)
   - Example: `display_order = 1` for "Computer + Amount 50k-100k + Bangalore"

2. **Create General Branches Next** (Higher display_order numbers)
   - Example: `display_order = 2` for "Amount 50k-100k" (any location)

3. **Create Default Branch Last** (Highest display_order)
   - Example: `display_order = 99` with no conditions

4. **Test Your Configuration**
   - Create test indents with various combinations
   - Verify correct branch is selected
   - Check approval routing is as expected

## Troubleshooting

**Problem**: Wrong branch is being selected
**Solution**: Check and adjust `display_order` values. Lower number = higher priority.

**Problem**: No branch matches
**Solution**: Add a default branch with no conditions as fallback.

**Problem**: Approvals going to wrong roles
**Solution**: Verify the approver configuration for the selected branch.

## Future Enhancements (Optional)

1. **Branch Selection Log**: Add detailed logging showing which branches matched and why one was selected
2. **Admin Dashboard**: Show branch matching statistics and conflicts
3. **Validation Tool**: Warn admin if branch conditions can overlap
4. **Testing Simulator**: Let admin test branch matching without creating actual indents
