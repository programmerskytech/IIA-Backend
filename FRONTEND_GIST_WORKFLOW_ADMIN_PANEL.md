# FRONTEND GIST: Workflow & Approval Admin Panel

## Overview:

Create an Admin Panel where you can:
1. ✅ View all workflows
2. ✅ Configure branches for each workflow (conditions/rules)
3. ✅ Configure approvers for each branch (who approves, in what order)
4. ✅ Set price limits and routing conditions
5. ✅ Activate/Deactivate branches and approvers

---

## Backend APIs Available:

### **Base URL:** `/api/admin/approvers`

| Method | Endpoint | Purpose |
|--------|----------|---------|
| GET | `/workflows/{workflowId}/branches` | Get all branches for a workflow |
| POST | `/workflows/{workflowId}/branches` | Create new branch |
| PUT | `/branches/{branchId}` | Update branch |
| DELETE | `/branches/{branchId}` | Delete branch |
| GET | `/workflow/{workflowId}/branch/{branchId}` | Get approvers for a branch |
| POST | `/` | Create approver |
| PUT | `/{approverId}` | Update approver |
| DELETE | `/{approverId}` | Delete approver |
| PUT | `/{approverId}/status` | Activate/Deactivate approver |

---

## Workflow List (Reference):

```javascript
const workflows = [
  { id: 1, name: 'Indent Approval Workflow', key: 'INDENT' },
  { id: 2, name: 'Tender Approver Workflow', key: 'TENDER_APPROVER' },
  { id: 3, name: 'Tender Evaluator Workflow', key: 'TENDER_EVALUATOR' },
  { id: 4, name: 'Purchase Order Workflow', key: 'PO' },
  { id: 5, name: 'Contingency Purchase Workflow', key: 'CP' }
];
```

---

## Frontend Implementation:

### **1. Workflow Management Page Structure**

```jsx
import React, { useState, useEffect } from 'react';
import {
  Tabs, Tab, Box, Typography, Button, Card, CardContent,
  Table, TableHead, TableBody, TableRow, TableCell,
  Dialog, DialogTitle, DialogContent, DialogActions,
  TextField, Select, MenuItem, FormControl, InputLabel,
  IconButton, Chip, Switch
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import axios from 'axios';

const WorkflowAdminPanel = () => {
  const [selectedWorkflow, setSelectedWorkflow] = useState(1);
  const [branches, setBranches] = useState([]);
  const [selectedBranch, setSelectedBranch] = useState(null);
  const [approvers, setApprovers] = useState([]);

  const workflows = [
    { id: 1, name: 'Indent Approval Workflow' },
    { id: 2, name: 'Tender Approver Workflow' },
    { id: 3, name: 'Tender Evaluator Workflow' },
    { id: 4, name: 'Purchase Order Workflow' },
    { id: 5, name: 'Contingency Purchase Workflow' }
  ];

  useEffect(() => {
    fetchBranches(selectedWorkflow);
  }, [selectedWorkflow]);

  const fetchBranches = async (workflowId) => {
    try {
      const response = await axios.get(
        `/api/admin/approvers/workflows/${workflowId}/branches`
      );
      setBranches(response.data?.responseData || []);
    } catch (error) {
      console.error('Error fetching branches:', error);
    }
  };

  const fetchApprovers = async (workflowId, branchId) => {
    try {
      const response = await axios.get(
        `/api/admin/approvers/workflow/${workflowId}/branch/${branchId}`
      );
      setApprovers(response.data?.responseData || []);
    } catch (error) {
      console.error('Error fetching approvers:', error);
    }
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>
        Workflow & Approval Management
      </Typography>

      {/* Workflow Tabs */}
      <Tabs value={selectedWorkflow} onChange={(e, v) => setSelectedWorkflow(v)}>
        {workflows.map(wf => (
          <Tab key={wf.id} label={wf.name} value={wf.id} />
        ))}
      </Tabs>

      {/* Branch Management Section */}
      <Box sx={{ mt: 3 }}>
        <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
          <Typography variant="h6">Workflow Branches</Typography>
          <Button
            variant="contained"
            startIcon={<AddIcon />}
            onClick={() => handleOpenBranchDialog()}
          >
            Add Branch
          </Button>
        </Box>

        {/* Branches Table */}
        <BranchTable
          branches={branches}
          onEdit={handleEditBranch}
          onDelete={handleDeleteBranch}
          onSelect={handleSelectBranch}
        />
      </Box>

      {/* Approver Management Section */}
      {selectedBranch && (
        <Box sx={{ mt: 4 }}>
          <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
            <Typography variant="h6">
              Approvers for: {selectedBranch.branchName}
            </Typography>
            <Button
              variant="contained"
              startIcon={<AddIcon />}
              onClick={() => handleOpenApproverDialog()}
            >
              Add Approver
            </Button>
          </Box>

          {/* Approvers Table */}
          <ApproverTable
            approvers={approvers}
            onEdit={handleEditApprover}
            onDelete={handleDeleteApprover}
            onStatusChange={handleApproverStatusChange}
          />
        </Box>
      )}
    </Box>
  );
};
```

---

### **2. Branch Table Component**

```jsx
const BranchTable = ({ branches, onEdit, onDelete, onSelect }) => {
  return (
    <Card>
      <CardContent>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Branch Code</TableCell>
              <TableCell>Branch Name</TableCell>
              <TableCell>Condition Type</TableCell>
              <TableCell>Conditions</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {branches.map(branch => (
              <TableRow
                key={branch.branchId}
                hover
                onClick={() => onSelect(branch)}
                sx={{ cursor: 'pointer' }}
              >
                <TableCell>{branch.branchCode}</TableCell>
                <TableCell>{branch.branchName}</TableCell>
                <TableCell>
                  <Chip label={branch.conditionType} size="small" />
                </TableCell>
                <TableCell>
                  {branch.conditionConfig ? (
                    <Typography variant="caption" component="pre">
                      {JSON.stringify(JSON.parse(branch.conditionConfig), null, 2)}
                    </Typography>
                  ) : (
                    'No conditions'
                  )}
                </TableCell>
                <TableCell>
                  <Chip
                    label={branch.isActive ? 'Active' : 'Inactive'}
                    color={branch.isActive ? 'success' : 'default'}
                    size="small"
                  />
                </TableCell>
                <TableCell>
                  <IconButton
                    size="small"
                    onClick={(e) => { e.stopPropagation(); onEdit(branch); }}
                  >
                    <EditIcon />
                  </IconButton>
                  <IconButton
                    size="small"
                    onClick={(e) => { e.stopPropagation(); onDelete(branch.branchId); }}
                  >
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </CardContent>
    </Card>
  );
};
```

---

### **3. Branch Dialog Component**

```jsx
const BranchDialog = ({ open, onClose, workflowId, branch, onSave }) => {
  const [formData, setFormData] = useState({
    branchCode: '',
    branchName: '',
    branchDescription: '',
    conditionType: 'DEFAULT',
    conditionConfig: '',
    isActive: true,
    displayOrder: 1
  });

  useEffect(() => {
    if (branch) {
      setFormData({
        ...branch,
        conditionConfig: branch.conditionConfig || ''
      });
    }
  }, [branch]);

  const conditionTypes = [
    { value: 'DEFAULT', label: 'Default (No conditions)' },
    { value: 'AMOUNT', label: 'Amount-Based' },
    { value: 'CATEGORY', label: 'Category-Based (Computer/Non-Computer)' },
    { value: 'LOCATION', label: 'Location-Based (Bangalore/Non-Bangalore)' },
    { value: 'PROJECT', label: 'Project-Based (Under Project/Not)' },
    { value: 'COMPOSITE', label: 'Composite (Multiple conditions)' },
    { value: 'AMOUNT_WITH_ROLE', label: 'Amount with Role' },
    { value: 'AMOUNT_WITH_PROJECT', label: 'Amount with Project' },
    { value: 'BID_TYPE', label: 'Bid Type' },
    { value: 'INDENT_COUNT', label: 'Indent Count' }
  ];

  const handleSubmit = async () => {
    try {
      const payload = {
        ...formData,
        workflowId,
        createdBy: 'admin' // Replace with logged-in user
      };

      if (branch) {
        // Update
        await axios.put(`/api/admin/approvers/branches/${branch.branchId}`, payload);
      } else {
        // Create
        await axios.post(`/api/admin/approvers/workflows/${workflowId}/branches`, payload);
      }

      onSave();
      onClose();
    } catch (error) {
      console.error('Error saving branch:', error);
      alert(error.response?.data?.message || 'Failed to save branch');
    }
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth>
      <DialogTitle>{branch ? 'Edit Branch' : 'Create Branch'}</DialogTitle>
      <DialogContent>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, mt: 2 }}>
          <TextField
            label="Branch Code"
            value={formData.branchCode}
            onChange={(e) => setFormData({ ...formData, branchCode: e.target.value })}
            required
            helperText="e.g., INDENT_PROJECT_COMPUTER"
          />

          <TextField
            label="Branch Name"
            value={formData.branchName}
            onChange={(e) => setFormData({ ...formData, branchName: e.target.value })}
            required
          />

          <TextField
            label="Description"
            value={formData.branchDescription}
            onChange={(e) => setFormData({ ...formData, branchDescription: e.target.value })}
            multiline
            rows={2}
          />

          <FormControl>
            <InputLabel>Condition Type</InputLabel>
            <Select
              value={formData.conditionType}
              onChange={(e) => setFormData({ ...formData, conditionType: e.target.value })}
            >
              {conditionTypes.map(ct => (
                <MenuItem key={ct.value} value={ct.value}>{ct.label}</MenuItem>
              ))}
            </Select>
          </FormControl>

          {formData.conditionType !== 'DEFAULT' && (
            <TextField
              label="Condition Configuration (JSON)"
              value={formData.conditionConfig}
              onChange={(e) => setFormData({ ...formData, conditionConfig: e.target.value })}
              multiline
              rows={6}
              helperText="Enter JSON configuration. Example: {\"minAmount\": 50000, \"projectBased\": true}"
            />
          )}

          <TextField
            label="Display Order"
            type="number"
            value={formData.displayOrder}
            onChange={(e) => setFormData({ ...formData, displayOrder: parseInt(e.target.value) })}
          />

          <FormControl>
            <Box display="flex" alignItems="center" gap={1}>
              <Typography>Status:</Typography>
              <Switch
                checked={formData.isActive}
                onChange={(e) => setFormData({ ...formData, isActive: e.target.checked })}
              />
              <Typography>{formData.isActive ? 'Active' : 'Inactive'}</Typography>
            </Box>
          </FormControl>
        </Box>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button onClick={handleSubmit} variant="contained">Save</Button>
      </DialogActions>
    </Dialog>
  );
};
```

---

### **4. Condition Config Examples**

```javascript
// For frontend guidance - show these examples to users

const conditionConfigExamples = {
  AMOUNT: {
    description: 'Amount-based routing',
    example: {
      minAmount: 50000,
      maxAmount: 100000
    }
  },
  CATEGORY: {
    description: 'Material category',
    example: {
      materialCategory: 'COMPUTER' // or 'NON_COMPUTER'
    }
  },
  LOCATION: {
    description: 'Location-based routing',
    example: {
      location: 'BANGALORE' // or 'NON_BANGALORE'
    }
  },
  PROJECT: {
    description: 'Project-based routing',
    example: {
      projectBased: true // or false
    }
  },
  COMPOSITE: {
    description: 'Multiple conditions combined',
    example: {
      projectBased: true,
      materialCategory: 'COMPUTER',
      location: 'BANGALORE'
    }
  },
  AMOUNT_WITH_ROLE: {
    description: 'Amount routing based on role',
    example: {
      role: ['Dean', 'Head SEG'],
      minAmountHeadSEG: 100000,
      minAmountDean: 150000
    }
  },
  AMOUNT_WITH_PROJECT: {
    description: 'Amount with project context',
    example: {
      minAmount: 50000,
      projectBased: true,
      aboveProjectSanctionLimit: false
    }
  },
  BID_TYPE: {
    description: 'Bid type for tender evaluation',
    example: {
      bidType: 'DOUBLE_BID', // or 'SINGLE_BID'
      department: 'PURCHASE' // optional
    }
  },
  INDENT_COUNT: {
    description: 'Number of indents in tender',
    example: {
      indentCount: 1, // exact count
      // OR
      minIndentCount: 2 // minimum count
    }
  }
};
```

---

### **5. Approver Table Component**

```jsx
const ApproverTable = ({ approvers, onEdit, onDelete, onStatusChange }) => {
  return (
    <Card>
      <CardContent>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Approver Code</TableCell>
              <TableCell>Role Name</TableCell>
              <TableCell>Level</TableCell>
              <TableCell>Sequence</TableCell>
              <TableCell>Parallel</TableCell>
              <TableCell>Mandatory</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {approvers
              .sort((a, b) => {
                if (a.approvalLevel !== b.approvalLevel) {
                  return a.approvalLevel - b.approvalLevel;
                }
                return a.approvalSequence - b.approvalSequence;
              })
              .map(approver => (
                <TableRow key={approver.approverId}>
                  <TableCell>{approver.approverCode}</TableCell>
                  <TableCell>
                    <Chip label={approver.roleName} color="primary" variant="outlined" />
                  </TableCell>
                  <TableCell>
                    <Chip label={`Level ${approver.approvalLevel}`} size="small" />
                  </TableCell>
                  <TableCell>{approver.approvalSequence}</TableCell>
                  <TableCell>
                    {approver.isParallelApproval ? (
                      <Chip label="Yes" size="small" color="info" />
                    ) : (
                      <Chip label="No" size="small" />
                    )}
                  </TableCell>
                  <TableCell>
                    {approver.isMandatory ? (
                      <Chip label="Yes" size="small" color="warning" />
                    ) : (
                      <Chip label="No" size="small" />
                    )}
                  </TableCell>
                  <TableCell>
                    <Switch
                      checked={approver.status === 'Active'}
                      onChange={() => onStatusChange(
                        approver.approverId,
                        approver.status === 'Active' ? 'Inactive' : 'Active'
                      )}
                      size="small"
                    />
                  </TableCell>
                  <TableCell>
                    <IconButton size="small" onClick={() => onEdit(approver)}>
                      <EditIcon />
                    </IconButton>
                    <IconButton size="small" onClick={() => onDelete(approver.approverId)}>
                      <DeleteIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))}
          </TableBody>
        </Table>
      </CardContent>
    </Card>
  );
};
```

---

### **6. Approver Dialog Component**

```jsx
const ApproverDialog = ({ open, onClose, workflowId, branchId, approver, onSave }) => {
  const [formData, setFormData] = useState({
    roleId: '',
    roleName: '',
    approvalLevel: 1,
    approvalSequence: 1,
    isParallelApproval: false,
    isMandatory: true,
    status: 'Active'
  });

  const [roles, setRoles] = useState([]);

  useEffect(() => {
    fetchRoles();
    if (approver) {
      setFormData(approver);
    }
  }, [approver]);

  const fetchRoles = async () => {
    // Fetch available roles from your role master API
    try {
      const response = await axios.get('/api/employee-department-master/roles');
      setRoles(response.data?.responseData || []);
    } catch (error) {
      console.error('Error fetching roles:', error);
    }
  };

  const handleSubmit = async () => {
    try {
      const payload = {
        ...formData,
        workflowId,
        branchId,
        createdBy: 'admin' // Replace with logged-in user
      };

      if (approver) {
        // Update
        await axios.put(`/api/admin/approvers/${approver.approverId}`, payload);
      } else {
        // Create
        await axios.post('/api/admin/approvers', payload);
      }

      onSave();
      onClose();
    } catch (error) {
      console.error('Error saving approver:', error);
      alert(error.response?.data?.message || 'Failed to save approver');
    }
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>{approver ? 'Edit Approver' : 'Add Approver'}</DialogTitle>
      <DialogContent>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, mt: 2 }}>
          <FormControl required>
            <InputLabel>Role</InputLabel>
            <Select
              value={formData.roleName}
              onChange={(e) => {
                const role = roles.find(r => r.roleName === e.target.value);
                setFormData({
                  ...formData,
                  roleId: role?.roleId || '',
                  roleName: e.target.value
                });
              }}
            >
              {roles.map(role => (
                <MenuItem key={role.roleId} value={role.roleName}>
                  {role.roleName}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <TextField
            label="Approval Level"
            type="number"
            value={formData.approvalLevel}
            onChange={(e) => setFormData({ ...formData, approvalLevel: parseInt(e.target.value) })}
            required
            helperText="Level 1, 2, 3, etc. - defines order of approval"
          />

          <TextField
            label="Approval Sequence"
            type="number"
            value={formData.approvalSequence}
            onChange={(e) => setFormData({ ...formData, approvalSequence: parseInt(e.target.value) })}
            required
            helperText="Sequence within the same level"
          />

          <FormControl>
            <Box display="flex" alignItems="center" gap={1}>
              <Typography>Parallel Approval (OR logic):</Typography>
              <Switch
                checked={formData.isParallelApproval}
                onChange={(e) => setFormData({ ...formData, isParallelApproval: e.target.checked })}
              />
            </Box>
            <Typography variant="caption" color="textSecondary">
              If enabled, any one approver at this level can approve
            </Typography>
          </FormControl>

          <FormControl>
            <Box display="flex" alignItems="center" gap={1}>
              <Typography>Mandatory Approval:</Typography>
              <Switch
                checked={formData.isMandatory}
                onChange={(e) => setFormData({ ...formData, isMandatory: e.target.checked })}
              />
            </Box>
          </FormControl>

          <FormControl>
            <InputLabel>Status</InputLabel>
            <Select
              value={formData.status}
              onChange={(e) => setFormData({ ...formData, status: e.target.value })}
            >
              <MenuItem value="Active">Active</MenuItem>
              <MenuItem value="Inactive">Inactive</MenuItem>
            </Select>
          </FormControl>
        </Box>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button onClick={handleSubmit} variant="contained">Save</Button>
      </DialogActions>
    </Dialog>
  );
};
```

---

## API Usage Examples:

### **1. Create Branch for Indent Workflow**

```javascript
// Example: Create branch for "Under Project - Computer - Bangalore"
const createBranch = async () => {
  const payload = {
    branchCode: 'INDENT_PROJECT_COMPUTER_BANGALORE',
    branchName: 'Under Project - Computer Category - Bangalore Location',
    branchDescription: 'Branch for indents under project with computer category in Bangalore',
    conditionType: 'COMPOSITE',
    conditionConfig: JSON.stringify({
      projectBased: true,
      materialCategory: 'COMPUTER',
      location: 'BANGALORE'
    }),
    isActive: true,
    displayOrder: 1,
    createdBy: 'admin'
  };

  const response = await axios.post(
    '/api/admin/approvers/workflows/1/branches',
    payload
  );

  console.log('Branch created:', response.data);
};
```

### **2. Add Approvers to Branch**

```javascript
// Example: Add Project Head as first approver
const addApprover = async (branchId) => {
  const payload = {
    workflowId: 1,
    branchId: branchId,
    roleId: 5, // From role master
    roleName: 'Project Head',
    approvalLevel: 1,
    approvalSequence: 1,
    isParallelApproval: false,
    isMandatory: true,
    status: 'Active',
    createdBy: 'admin'
  };

  const response = await axios.post('/api/admin/approvers', payload);
  console.log('Approver added:', response.data);
};
```

### **3. Create Amount-Based Branch**

```javascript
// Example: PO under 50,000
const createAmountBranch = async () => {
  const payload = {
    branchCode: 'PO_UNDER_50000',
    branchName: 'Purchase Order Under 50,000',
    branchDescription: 'PO with total amount less than 50,000',
    conditionType: 'AMOUNT',
    conditionConfig: JSON.stringify({
      maxAmount: 50000
    }),
    isActive: true,
    displayOrder: 1,
    createdBy: 'admin'
  };

  const response = await axios.post(
    '/api/admin/approvers/workflows/4/branches', // PO Workflow
    payload
  );
};
```

---

## Testing Checklist:

### Branch Management:
- [ ] Can view all branches for a workflow
- [ ] Can create new branch with conditions
- [ ] Can edit existing branch
- [ ] Can delete branch
- [ ] Can activate/deactivate branch
- [ ] Condition JSON is validated

### Approver Management:
- [ ] Can view approvers for a branch
- [ ] Can add new approver with role selection
- [ ] Can set approval level and sequence
- [ ] Can configure parallel approval
- [ ] Can set mandatory/optional approval
- [ ] Can edit approver details
- [ ] Can delete approver
- [ ] Can change approver status

### Workflow Routing:
- [ ] Branches with conditions work correctly
- [ ] Amount-based routing works
- [ ] Category-based routing works
- [ ] Location-based routing works
- [ ] Project-based routing works
- [ ] Multi-level approvals flow correctly

---

## Summary:

✅ Backend provides complete REST APIs for workflow management

✅ Frontend needs to implement:
- Workflow selection tabs
- Branch CRUD operations
- Approver CRUD operations
- JSON condition configuration
- Status toggle switches

✅ All 5 workflows can be fully configured from Admin Panel

✅ No code changes needed for new branches or approvers

---
