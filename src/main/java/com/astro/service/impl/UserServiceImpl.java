package com.astro.service.impl;

import com.astro.constant.AppConstant;
import com.astro.dto.workflow.LoginRoleDto;
import com.astro.dto.workflow.UserDto;
import com.astro.dto.workflow.UserRoleDto;
import com.astro.dto.workflow.userRequestDto;
import com.astro.entity.EmployeeDepartmentMaster;
import com.astro.entity.RoleMaster;
import com.astro.entity.UserMaster;
import com.astro.exception.BusinessException;
import com.astro.entity.UserRoleMaster;
import com.astro.exception.ErrorDetails;
import com.astro.exception.InvalidInputException;
import com.astro.repository.EmployeeDepartmentMasterRepository;
import com.astro.repository.RoleMasterRepository;
import com.astro.repository.UserMasterRepository;
import com.astro.repository.UserRoleMasterRepository;
import com.astro.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMasterRepository userMasterRepository;

    @Autowired
    UserRoleMasterRepository userRoleMasterRepository;

    @Autowired
    RoleMasterRepository roleMasterRepository;
    
    @Autowired
    EmployeeDepartmentMasterRepository employeeRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void validateUser(Integer userId) {
        UserMaster userMaster = userMasterRepository.findById(userId)
            .orElseThrow(() -> new InvalidInputException(
                new ErrorDetails(
                    AppConstant.USER_NOT_FOUND, 
                    AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, 
                    "User not found."
                )
            ));
    }

    @Override
    public UserRoleDto login(UserDto userDto) {
        UserRoleDto userRoleDto = null;

        if(Objects.nonNull(userDto.getUserId()) && Objects.nonNull(userDto.getPassword())){
            UserMaster userMaster = userMasterRepository.findById(userDto.getUserId())
                .orElse(null);
                
            if(Objects.isNull(userMaster)){
                throw new InvalidInputException(
                    new ErrorDetails(
                        AppConstant.USER_NOT_FOUND, 
                        AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION, 
                        "User not found."
                    )
                );
            }
            
            // Verify encrypted password
            if(!passwordEncoder.matches(userDto.getPassword(), userMaster.getPassword())){
                throw new InvalidInputException(
                    new ErrorDetails(
                        AppConstant.USER_NOT_FOUND, 
                        AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION, 
                        "Invalid credentials."
                    )
                );
            }

            List<UserRoleMaster> userRoles = userRoleMasterRepository.findAllByUserId(userMaster.getUserId());
            Optional<EmployeeDepartmentMaster> employee = employeeRepo.findByEmployeeId(userMaster.getEmployeeId());

            userRoleDto = new UserRoleDto();
            userRoleDto.setUserId(userMaster.getUserId());
            userRoleDto.setCreatedBy(userMaster.getCreatedBy());
            userRoleDto.setUserName(userMaster.getUserName());
            userRoleDto.setMobileNumber(userMaster.getMobileNumber());
            userRoleDto.setEmail(userMaster.getEmail());
            
            List<LoginRoleDto> roleDtos = userRoles.stream().map(role -> {
                LoginRoleDto dto = new LoginRoleDto();
                dto.setUserRoleId(role.getUserRoleId());
                dto.setRoleId(role.getRoleId());
                dto.setRoleName(roleNameById(role.getRoleId()));
                dto.setReadPermission(role.getReadPermission());
                dto.setWritePermission(role.getWritePermission());
                return dto;
            }).collect(Collectors.toList());
            
            userRoleDto.setRoles(roleDtos);
            
            if(employee.isPresent()){
                EmployeeDepartmentMaster emp = employee.get();
                userRoleDto.setEmployeeDepartment(emp.getDepartmentName());
            } else {
                userRoleDto.setEmployeeDepartment(null);
            }
        } else {
            throw new InvalidInputException(
                new ErrorDetails(
                    AppConstant.USER_INVALID_INPUT, 
                    AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, 
                    "Invalid input."
                )
            );
        }

        return userRoleDto;
    }

    private String roleNameById(Integer roleId) {
        if(Objects.nonNull(roleId)) {
            return roleMasterRepository.findById(roleId)
                .orElse(new RoleMaster())
                .getRoleName();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public UserDto createUser(userRequestDto userDto) {
        return createUserWithEncryption(userDto);
    }
    
    @Override
    @Transactional
    public UserDto createUserWithEncryption(userRequestDto userDto) {
        // Validate required fields
        if(userDto.getUserName() == null || userDto.getUserName().trim().isEmpty()) {
            throw new BusinessException(
                new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_VALIDATION,
                    "Username is required"
                )
            );
        }

        if(userDto.getEmail() == null || userDto.getEmail().trim().isEmpty()) {
            throw new BusinessException(
                new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_VALIDATION,
                    "Email is required"
                )
            );
        }

        if(userDto.getPassword() == null || userDto.getPassword().trim().isEmpty()) {
            throw new BusinessException(
                new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_VALIDATION,
                    "Password is required"
                )
            );
        }

        // Check if user with this employee ID already exists (only if employeeId is provided)
        if(userDto.getEmployeeId() != null && !userDto.getEmployeeId().trim().isEmpty()) {
            Optional<UserMaster> existingUser = userMasterRepository.findByEmployeeId(userDto.getEmployeeId());
            if(existingUser.isPresent()) {
                throw new BusinessException(
                    new ErrorDetails(
                        AppConstant.ERROR_CODE_RESOURCE,
                        AppConstant.ERROR_TYPE_CODE_RESOURCE,
                        AppConstant.ERROR_TYPE_VALIDATION,
                        "User already exists for this employee ID"
                    )
                );
            }
        }

        UserMaster userMaster = new UserMaster();
        userMaster.setUserName(userDto.getUserName());

        // Set mobileNumber - use empty string if null to avoid database constraint issues
        userMaster.setMobileNumber(userDto.getMobileNumber() != null ? userDto.getMobileNumber() : "");

        // Encrypt password before saving
        userMaster.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userMaster.setEmail(userDto.getEmail());
        userMaster.setCreatedBy(userDto.getCreatedBy());

        // Set employeeId - use empty string if null
        userMaster.setEmployeeId(userDto.getEmployeeId() != null ? userDto.getEmployeeId() : "");

        // Determine role names - support both single roleName and list roleNames
        List<String> rolesToAssign = new ArrayList<>();
        if (userDto.getRoleNames() != null && !userDto.getRoleNames().isEmpty()) {
            rolesToAssign = userDto.getRoleNames();
        } else if (userDto.getRoleName() != null && !userDto.getRoleName().trim().isEmpty()) {
            rolesToAssign.add(userDto.getRoleName());
        }

        // Don't save role_name in user_master - roles are properly stored in user_role_master table
        userMaster.setRoleName(null);

        // Save user
        userMasterRepository.save(userMaster);

        // Save each role in user_role_master
        for (String roleName : rolesToAssign) {
            RoleMaster role = roleMasterRepository.findFirstByRoleName(roleName)
                .orElseThrow(() -> new BusinessException(
                    new ErrorDetails(
                        AppConstant.ERROR_CODE_RESOURCE,
                        AppConstant.ERROR_TYPE_CODE_RESOURCE,
                        AppConstant.ERROR_TYPE_VALIDATION,
                        "Role with name '" + roleName + "' not found."
                    )
                ));

            UserRoleMaster userRole = new UserRoleMaster();
            userRole.setUserId(userMaster.getUserId());
            userRole.setRoleId(role.getRoleId());
            userRole.setReadPermission(true);
            userRole.setWritePermission(true);
            userRole.setCreatedBy(userDto.getCreatedBy());
            userRole.setCreatedDate(new Date());
            userRoleMasterRepository.save(userRole);
        }

        return mapToResponseDTO(userMaster);
    }

    @Override
    @Transactional
    public UserDto updateUser(int userId, userRequestDto userDto) {
        UserMaster userMaster = userMasterRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(
                new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_VALIDATION,
                    "User not found for the provided user ID."
                )
            ));

        userMaster.setUserName(userDto.getUserName());
        
        // Only update password if a new one is provided
        if(userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            userMaster.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        
        userMaster.setMobileNumber(userDto.getMobileNumber());
        userMaster.setEmail(userDto.getEmail());
        userMaster.setCreatedBy(userDto.getCreatedBy());
        
        userMasterRepository.save(userMaster);
        return mapToResponseDTO(userMaster);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserMaster> userMasters = userMasterRepository.findAll();
        return userMasters.stream()
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(int userId) {
        UserMaster userMaster = userMasterRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(
                new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_VALIDATION,
                    "User not found for the provided user ID."
                )
            ));
        return mapToResponseDTO(userMaster);
    }

    @Override
    public Optional<UserMaster> getUserMasterByCreatedBy(String createdBy) {
        return userMasterRepository.findByCreatedBy(createdBy);
    }

    @Override
    @Transactional
    public void deleteUser(int userId) {
        UserMaster userMaster = userMasterRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(
                new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_VALIDATION,
                    "User not found for the provided ID."
                )
            ));
        try {
            userMasterRepository.delete(userMaster);
        } catch (Exception ex) {
            throw new BusinessException(
                new ErrorDetails(
                    AppConstant.INTER_SERVER_ERROR,
                    AppConstant.ERROR_TYPE_CODE_INTERNAL,
                    AppConstant.ERROR_TYPE_VALIDATION,
                    "An error occurred while deleting the user."
                ),
                ex
            );
        }
    }
    
    @Override
    @Transactional
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        UserMaster userMaster = userMasterRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(
                new ErrorDetails(
                    AppConstant.USER_NOT_FOUND,
                    AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION,
                    "User not found."
                )
            ));
            
        // Verify old password
        if(!passwordEncoder.matches(oldPassword, userMaster.getPassword())) {
            throw new BusinessException(
                new ErrorDetails(
                    AppConstant.USER_INVALID_INPUT,
                    AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION,
                    "Old password is incorrect."
                )
            );
        }
        
        // Set new encrypted password
        userMaster.setPassword(passwordEncoder.encode(newPassword));
        userMasterRepository.save(userMaster);
    }
    
    @Override
    public boolean userExistsByEmployeeId(String employeeId) {
        return userMasterRepository.findByEmployeeId(employeeId).isPresent();
    }

    private UserDto mapToResponseDTO(UserMaster userMaster) {
        UserDto userDto = new UserDto();
        userDto.setUserId(userMaster.getUserId());
        userDto.setUserName(userMaster.getUserName());
        // Don't return password in DTO
        userDto.setPassword(null);
        userDto.setEmail(userMaster.getEmail());
        userDto.setMobileNumber(userMaster.getMobileNumber());
        userDto.setEmployeeId(userMaster.getEmployeeId());
        userDto.setRoleName(userMaster.getRoleName());
        userDto.setCreatedDate(userMaster.getCreatedDate());
        userDto.setCreatedBy(userMaster.getCreatedBy());
        return userDto;
    }
}