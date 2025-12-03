package com.astro.config;

import com.astro.entity.RoleMaster;
import com.astro.entity.UserMaster;
import com.astro.entity.UserRoleMaster;
import com.astro.entity.EmployeeDepartmentMaster;
import com.astro.repository.RoleMasterRepository;
import com.astro.repository.UserMasterRepository;
import com.astro.repository.UserRoleMasterRepository;
import com.astro.repository.EmployeeDepartmentMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserMasterRepository userMasterRepository;

    @Autowired
    private RoleMasterRepository roleMasterRepository;

    @Autowired
    private UserRoleMasterRepository userRoleMasterRepository;
    
    @Autowired
    private EmployeeDepartmentMasterRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeAdminRole();
        initializeDefaultAdmin();
    }

    private void initializeAdminRole() {
        Optional<RoleMaster> adminRole = roleMasterRepository.findByRoleName("Admin");
        
        if(!adminRole.isPresent()) {
            RoleMaster newAdminRole = new RoleMaster();
            newAdminRole.setRoleName("Admin");
            newAdminRole.setCreatedBy("SYSTEM");
            newAdminRole.setCreatedDate(LocalDateTime.now());
            roleMasterRepository.save(newAdminRole);
            System.out.println("✅ Admin role created successfully");
        } else {
            System.out.println("ℹ️ Admin role already exists");
        }
    }

    private void initializeDefaultAdmin() {
        long userCount = userMasterRepository.count();
        
        if(userCount == 0) {
            System.out.println("🔧 No users found. Creating default admin...");
            
            // Create admin employee first
            EmployeeDepartmentMaster adminEmployee = new EmployeeDepartmentMaster();
            adminEmployee.setEmployeeId("ADMIN001");
            adminEmployee.setEmployeeName("System Administrator");
            adminEmployee.setDepartmentName("IT");
            adminEmployee.setDesignation("Admin");
            adminEmployee.setLocation("Bangalore");
            adminEmployee.setPhoneNumber("9999999999");
            adminEmployee.setEmailAddress("admin@iia.com");
            adminEmployee.setAddress("IIA Headquarters");
            adminEmployee.setStatus("Active");
            adminEmployee.setCreatedBy("SYSTEM");
            adminEmployee.setUpdatedBy("SYSTEM");
            adminEmployee.setCreatedDate(LocalDateTime.now());
            adminEmployee.setUpdatedDate(LocalDateTime.now());
            adminEmployee.setIsDraft(false);
            
            employeeRepository.save(adminEmployee);
            System.out.println("✅ Admin employee created: ADMIN001");
            
            // Create admin user
            UserMaster adminUser = new UserMaster();
            adminUser.setUserName("admin");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setEmail("admin@iia.com");
            adminUser.setMobileNumber("9999999999");
            adminUser.setEmployeeId("ADMIN001");
            adminUser.setRoleName("Admin");
            adminUser.setCreatedBy("SYSTEM");
            adminUser.setCreatedDate(LocalDateTime.now());
            
            userMasterRepository.save(adminUser);
            
            // Assign Admin role
            RoleMaster adminRole = roleMasterRepository.findByRoleName("Admin")
                .orElseThrow(() -> new RuntimeException("Admin role not found"));
            
            UserRoleMaster userRole = new UserRoleMaster();
            userRole.setUserId(adminUser.getUserId());
            userRole.setRoleId(adminRole.getRoleId());
            userRole.setReadPermission(true);
            userRole.setWritePermission(true);
            userRole.setCreatedBy("SYSTEM");
            userRole.setCreatedDate(new Date());
            
            userRoleMasterRepository.save(userRole);
            System.out.println("✅ Admin role assigned successfully");
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("🎉 DEFAULT ADMIN ACCOUNT CREATED");
            System.out.println("=".repeat(60));
            System.out.println("Employee ID: ADMIN001");
            System.out.println("User ID: " + adminUser.getUserId());  // ✅ CHANGED: Show userId instead of username
            System.out.println("Username: admin");
            System.out.println("Password: admin123");
            System.out.println("\n⚠️  LOGIN INSTRUCTIONS:");
            System.out.println("   Use User ID: " + adminUser.getUserId());  // ✅ CHANGED: Clarify login field
            System.out.println("   Use Password: admin123");
            System.out.println("\n⚠️  PLEASE CHANGE THE PASSWORD AFTER FIRST LOGIN");
            System.out.println("=".repeat(60) + "\n");
        } else {
            System.out.println("ℹ️ Users already exist in the system. Skipping admin creation.");
        }
    }
}