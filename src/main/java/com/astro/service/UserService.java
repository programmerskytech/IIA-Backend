package com.astro.service;

import com.astro.dto.workflow.UserDto;
import com.astro.dto.workflow.userRequestDto;
import com.astro.entity.UserMaster;


import java.util.List;
import java.util.Optional;

import com.astro.dto.workflow.UserDto;
import com.astro.dto.workflow.UserRoleDto;

public interface UserService {

    public void validateUser(Integer userId);

    public UserDto createUser(userRequestDto userDto);
    public UserDto updateUser(int userId, userRequestDto userDto);
    public List<UserDto> getAllUsers();
    public UserDto getUserById(int userId);

   // public UserMaster getUserByCreatedBy(String createdBy);
    public Optional<UserMaster> getUserMasterByCreatedBy(String createdBy);
    public void deleteUser(int userId);


    UserRoleDto login(UserDto userDto);
    /*public UserDetailDto getUserDetails(LoginDto loginDto);
    public UserDetailDto updatePassword(LoginDto loginDto);
    public UserDetailDto registerUser(RegistrationDto registrationDto);
    public UserDetailDto updateUserDetails(RegistrationDto registrationDto);
    public void deleteUser(UserIdDto userIdDto);
    public void activateUser(UserIdDto userIdDto);
    public void deactivateUser(UserIdDto userIdDto);
    //public void addBasicDetails(LoanDetailsDto loanDetailsDto);
    //public LoanDetailsDto getBasicDetails(LoanDetailsDto loanDetailsDto);
    //public void removeBasicDetails(LoanDetailsDto loanDetailsDto);
    public void addContactUs(ContactUsDto contactUsDto);

    public String uploadProfileImage(MultipartFile file, String userId);
    public Map<String, Object> downloadProfileImage(String userId);
    public boolean removeProfileImage(String userId);
    List<UserDetailDto> getAllUserDetails();*/
}
