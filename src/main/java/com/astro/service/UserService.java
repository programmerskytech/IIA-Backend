package com.astro.service;

import com.astro.dto.workflow.UserDto;
import com.astro.dto.workflow.UserSearchResponseDto;
import com.astro.dto.workflow.userRequestDto;
import com.astro.entity.UserMaster;

import java.util.List;
import java.util.Optional;

import com.astro.dto.workflow.UserRoleDto;

public interface UserService {

    public void validateUser(Integer userId);

    public UserDto createUser(userRequestDto userDto);
    
    public UserDto createUserWithEncryption(userRequestDto userDto);
    
    public UserDto updateUser(int userId, userRequestDto userDto);
    
    public List<UserDto> getAllUsers();
    
    public UserDto getUserById(int userId);

    public Optional<UserMaster> getUserMasterByCreatedBy(String createdBy);
    
    public void deleteUser(int userId);

    UserRoleDto login(UserDto userDto);
    
    // New method for changing password
    public void changePassword(Integer userId, String oldPassword, String newPassword);
    
    // Method to check if user exists by employee ID
    public boolean userExistsByEmployeeId(String employeeId);

    // Search users by keyword (username, email, mobile, employee ID, employee name)
    public List<UserSearchResponseDto> searchUsers(String keyword);

    // Get all users with their roles for listing
    public List<UserSearchResponseDto> getAllUsersWithRoles();

    // Activate/Deactivate user (soft delete)
    public UserDto toggleUserStatus(int userId);
    public UserDto activateUser(int userId);
    public UserDto deactivateUser(int userId);
}