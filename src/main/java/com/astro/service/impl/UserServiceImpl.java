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

import com.astro.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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


    @Override
    public void validateUser(Integer userId) {
       UserMaster userMaster = userMasterRepository.findById(userId).orElseThrow(() -> new InvalidInputException(new ErrorDetails(AppConstant.USER_NOT_FOUND, AppConstant.ERROR_TYPE_CODE_VALIDATION,
               AppConstant.ERROR_TYPE_VALIDATION, "User not found.")));
    }

    @Override
    public UserRoleDto login(UserDto userDto) {
        UserRoleDto userRoleDto = null;

        if(Objects.nonNull(userDto.getUserId()) && Objects.nonNull(userDto.getPassword())){

            UserMaster userMaster = userMasterRepository.findByUserIdAndPassword(userDto.getUserId(), userDto.getPassword());
            if(Objects.isNull(userMaster)){
                throw new InvalidInputException(new ErrorDetails(AppConstant.USER_NOT_FOUND, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION, "User not found."));
            }

        //    UserRoleMaster userRoleMaster = userRoleMasterRepository.findByUserId(userMaster.getUserId());
            List<UserRoleMaster> userRoles = userRoleMasterRepository.findAllByUserId(userMaster.getUserId());

            Optional<EmployeeDepartmentMaster> employee = employeeRepo.findByEmployeeId(userMaster.getEmployeeId());

            userRoleDto = new UserRoleDto();
            userRoleDto.setUserId(userMaster.getUserId());
           // userRoleDto.setUserRoleId(userRoleMaster.getUserRoleId());
         //   userRoleDto.setRoleId(userRoleMaster.getRoleId());
           // userRoleDto.setCreatedDate(CommonUtils.convertSqlDateToString(userMaster.getCreatedDate()));
            userRoleDto.setCreatedBy(userMaster.getCreatedBy());
          //  userRoleDto.setReadPermission(userRoleMaster.getReadPermission());
          //  userRoleDto.setWritePermission(userRoleMaster.getWritePermission());
          //  userRoleDto.setRole(roleNameById(userRoleMaster.getRoleId()));
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
            }else {
                userRoleDto.setEmployeeDepartment(null);
            }
        }else{
            throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "Invalid input."));
        }

        return userRoleDto;
    }

    private String roleNameById(Integer roleId) {
        if(Objects.nonNull(roleId)) {
            return roleMasterRepository.findById(roleId).orElse(new RoleMaster()).getRoleName();
        }else{
            return null;
        }
    }

  /*  @Override
    public UserDto createUser(userRequestDto userDto) {

        UserMaster userMaster = new UserMaster();
     /*   Optional<UserMaster> existingUser = userMasterRepository.findByUserName(userDto.getUserName());
        if (existingUser.isPresent()) {
            throw new BusinessException(
                    new ErrorDetails(
                            AppConstant.ERROR_CODE_RESOURCE,
                            AppConstant.ERROR_TYPE_CODE_RESOURCE,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "User with username '" + userDto.getUserName() + "' already exists.")
            );
        }

      */
     /*   EmployeeDepartmentMaster employee = employeeRepo.findById(userDto.getEmployeeId())
                .orElseThrow(() -> new BusinessException( new ErrorDetails(
                        AppConstant.ERROR_CODE_RESOURCE,
                        AppConstant.ERROR_TYPE_CODE_RESOURCE,
                        AppConstant.ERROR_TYPE_VALIDATION,
                        "User with username '" + userDto.getUserName() + "' already exists.")
                ));*/

      /*  userMaster.setUserName(userDto.getUserName());
        userMaster.setRoleName(userDto.getRoleName());
        userMaster.setMobileNumber(userDto.getMobileNumber());
        userMaster.setPassword(userDto.getPassword());
        userMaster.setEmail(userDto.getEmail());
        userMaster.setCreatedBy(userDto.getCreatedBy());
        userMaster.setEmployeeId(userDto.getEmployeeId());
        userMasterRepository.save(userMaster);
        // Step 2: Fetch the role where roleName matches userName
        RoleMaster role = roleMasterRepository.findByRoleName(userDto.getRoleName())
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "Role with name '" + userDto.getUserName() + "' not found.")
                ));
        // Step 3: Save the user-role mapping in user_role_master
        UserRoleMaster userRole = new UserRoleMaster();
        userRole.setUserId(userMaster.getUserId());
        userRole.setRoleId(role.getRoleId());
        userRole.setReadPermission(true);
        userRole.setWritePermission(true);
        userRole.setCreatedBy(userDto.getCreatedBy());
        userRole.setCreatedDate(new Date());

        userRoleMasterRepository.save(userRole);
       // employee.setUserId(userMaster.getUserId());
       // employee.setUpdatedBy(userDto.getCreatedBy());
      //  employee.setUpdatedDate(LocalDateTime.now());
      //  employeeRepo.save(employee);
        return mapToResponseDTO(userMaster);
    }*/
      @Override
      public UserDto createUser(userRequestDto userDto) {
          UserMaster userMaster = new UserMaster();
          userMaster.setUserName(userDto.getUserName());
          userMaster.setMobileNumber(userDto.getMobileNumber());
          userMaster.setPassword(userDto.getPassword());
          userMaster.setEmail(userDto.getEmail());
          userMaster.setCreatedBy(userDto.getCreatedBy());
          userMaster.setEmployeeId(userDto.getEmployeeId());

          // Save role names as comma-separated string in user_master
          String rolesAsString = String.join(",", userDto.getRoleNames());
          userMaster.setRoleName(rolesAsString);

          // Save user
          userMasterRepository.save(userMaster);

          // Save each role in user_role_master
          for (String roleName : userDto.getRoleNames()) {
              RoleMaster role = roleMasterRepository.findByRoleName(roleName)
                      .orElseThrow(() -> new BusinessException(
                              new ErrorDetails(
                                      AppConstant.ERROR_CODE_RESOURCE,
                                      AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                      AppConstant.ERROR_TYPE_VALIDATION,
                                      "Role with name '" + roleName + "' not found.")
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
    public UserDto updateUser(int userId,userRequestDto userDto) {
       UserMaster userMaster = userMasterRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "user not found for the provided user ID.")
                ));


       userMaster.setUserName(userDto.getUserName());
       userMaster.setPassword(userDto.getPassword());
       userMaster.setMobileNumber(userDto.getMobileNumber());
       userMaster.setCreatedBy(userDto.getCreatedBy());
       userMasterRepository.save(userMaster);
        return mapToResponseDTO(userMaster);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<UserMaster> userMasters = userMasterRepository.findAll();
        return userMasters.stream().map(this::mapToResponseDTO).collect(Collectors.toList());

    }

    @Override
    public UserDto getUserById(int userId) {

        UserMaster userMaster = userMasterRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "user not found for the provided user ID.")
                ));
        return mapToResponseDTO(userMaster);
    }

    @Override
    public Optional<UserMaster> getUserMasterByCreatedBy(String createdBy) {
        return userMasterRepository.findByCreatedBy(createdBy);
    }

    @Override
    public void deleteUser(int userId) {
       UserMaster userMaster = userMasterRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "user not found for the provided ID."
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
    private UserDto mapToResponseDTO(UserMaster userMaster) {

        UserDto userDto = new UserDto();
        userDto.setUserId(userMaster.getUserId());
        userDto.setUserName(userMaster.getUserName());
        userDto.setPassword(userMaster.getPassword());
        userDto.setMobileNumber(userMaster.getMobileNumber());
        userDto.setRoleName(userMaster.getRoleName());
        userDto.setCreatedDate(userMaster.getCreatedDate());
        userDto.setCreatedBy(userMaster.getCreatedBy());
        return userDto;
    }
    public UserMaster getUserByCreatedBy(String createdBy) {
        return userMasterRepository.findByCreatedBy(createdBy).orElse(null);
    }

    /*private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private BasicDetailsRepository basicDetailsRepository;

    @Autowired
    private ContactUsRepository contactUsRepository;

    @Autowired
    private CsrDetailsRepository csrDetailsRepository;

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private CAGTRepository cagtRepository;

    @Autowired
    BlobServiceClient blobServiceClient;

    @Autowired
    BlobContainerClient blobContainerClient;

    @Override
    public UserDetailDto getUserDetails(LoginDto loginDto) {
        UserDetailDto userDetailDto = null;
        UserDetails userDetails = null;

        if (Objects.nonNull(loginDto.getMobileNumber())) {
            userDetails = userRepository.findByUserIdMobileNumber(loginDto.getMobileNumber());
        } else {
            userDetails = userRepository.findByUserIdId(loginDto.getUserId());
        }

        if (Objects.nonNull((userDetails))) {
            userDetailDto = userDetailDtoConvertor(userDetails);
        } else {
            throw new UnauthorizedException(new ErrorDetails(AppConstant.USER_NOT_FOUND, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "User not found."));
        }

        return userDetailDto;
    }

    @Override
    public UserDetailDto updatePassword(LoginDto loginDto) {
        if(Objects.nonNull(loginDto) && Objects.nonNull(loginDto.getUserId()) && Objects.nonNull(loginDto.getPassword()) && Objects.nonNull(loginDto.getOldPassword())) {
            UserDetails userDetails =  validateUser(loginDto.getUserId());
            String oldPassword = Base64.getEncoder().encodeToString(loginDto.getOldPassword().getBytes());
            if(!oldPassword.equals(userDetails.getPassword())){
                throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION, "Old password not correct."));
            }

            userDetails.setPassword(Base64.getEncoder().encodeToString(loginDto.getPassword().getBytes()));
            userRepository.save(userDetails);
            return userDetailDtoConvertor(userDetails);
        }else{
            throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "Invalid input."));
        }
    }

    private UserDetailDto userDetailDtoConvertor(UserDetails userDetails) {
        UserDetailDto userDetailDto = new UserDetailDto();
        userDetailDto.setUserId(userDetails.getUserId().getId());
        userDetailDto.setActive(userDetails.getActive() == 'Y' ? true : false);
        userDetailDto.setAdminFlag(userDetails.getAdminFlag() == 'Y' ? true : false);
        userDetailDto.setUserMode(userDetails.getUserMode());
        userDetailDto.setUserType(userDetails.getUserType());
        userDetailDto.setCreatedBy(userDetails.getCreatedBy());
        userDetailDto.setCreatedDate(userDetails.getCreatedDate());
        userDetailDto.setEmailId(userDetails.getEmailId());
        userDetailDto.setFirstName(userDetails.getFirstName());
        userDetailDto.setLastName(userDetails.getLastName());
        userDetailDto.setMobileNumber(userDetails.getUserId().getMobileNumber());
        userDetailDto.setLoanId(getLoanIdByUserId(userDetails.getUserId().getId()));
        userDetailDto.setAddressLineFirst(userDetails.getAddressLineFirst());
        userDetailDto.setAddressLineSecond(userDetails.getAddressLineSecond());
        userDetailDto.setState(userDetails.getState());
        userDetailDto.setCity(userDetails.getCity());
        userDetailDto.setPinCode(userDetails.getPinCode());
        userDetailDto.setRemarks(userDetails.getRemarks());

        return userDetailDto;
    }

    @Override
    @Transactional
    public UserDetailDto registerUser(RegistrationDto registrationDto) {
        UserDetails userDetails;

        if(Objects.nonNull(registrationDto.getMobileNumber())) {
            userDetails = userRepository.findByUserIdMobileNumber(registrationDto.getMobileNumber());
            if(Objects.isNull(userDetails)) {
                UserId userId = new UserId();
                userId.setUserId(String.valueOf(sequenceService.getNextValueBySequenceType(registrationDto.getCategory())));
                userId.setMobileNumber(registrationDto.getMobileNumber());

                userDetails = new UserDetails();
                userDetails.setUserId(userId);
                userDetails.setFirstName(registrationDto.getFirstName());
                userDetails.setLastName(registrationDto.getLastName());
                userDetails.setUserType(registrationDto.getCategory().getValue());
                userDetails.setEmailId(registrationDto.getEmail());
                if(registrationDto.getCategory().getValue().equalsIgnoreCase(UserType.CSR.getValue())) {
                    userDetails.setActive('Y');
                }else{
                    userDetails.setActive('N');
                }
                userDetails.setUserMode(moduleService.getModuleByUserType(registrationDto.getCategory()));
                userDetails.setAdminFlag('N');
                userDetails.setCreatedBy("esavari");
                userDetails.setCreatedDate(new Date());
                userDetails.setEmailId(registrationDto.getEmail());
                userDetails.setUserType(registrationDto.getCategory().getValue());
                if (Objects.nonNull(registrationDto.getPassword())) {
                    userDetails.setPassword(Base64.getEncoder().encodeToString(registrationDto.getPassword().getBytes()));
                }

                userRepository.save(userDetails);

                //addUserMaster(userDetails);
                return userDetailDtoConvertor(userDetails);
            }else{
                throw new InvalidInputException(new ErrorDetails(AppConstant.USER_ALREADY_EXISTS, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                                      AppConstant.ERROR_TYPE_VALIDATION, "User already available with the same mobile number."));
            }
        }
        return null;

    }

    private void addUserMaster(UserDetails userDetails) {
        //adding dealer data in DLR master
        if(UserType.DLR.getValue().equalsIgnoreCase(userDetails.getUserType())){
            DealerMaster dealerMaster = new DealerMaster();
            dealerMaster.setDealerId(userDetails.getUserId().getId());
            dealerMaster.setName(userDetails.getFirstName() + " " + userDetails.getLastName());
            dealerMaster.setCreatedBy("Admin");
            dealerMaster.setCreatedDate(new Date());
            dealerMaster.setEmail(userDetails.getEmailId());
            dealerMaster.setFlag('N');
            dealerMaster.setMobile(userDetails.getUserId().getMobileNumber());
            dealerRepository.save(dealerMaster);
        }else if(UserType.CAGT.getValue().equalsIgnoreCase(userDetails.getUserType())){
            CAGTMaster cagtMaster = new CAGTMaster();
            cagtMaster.setCagtId(userDetails.getUserId().getId());
            cagtMaster.setName(userDetails.getFirstName() + " " + userDetails.getLastName());
            cagtMaster.setCreatedBy("Admin");
            cagtMaster.setCreatedDate(new Date());
            cagtMaster.setEmail(userDetails.getEmailId());
            cagtMaster.setFlag('N');
            cagtMaster.setMobile(userDetails.getUserId().getMobileNumber());

            cagtRepository.save(cagtMaster);
        }

    }

    @Override
    public UserDetailDto updateUserDetails(RegistrationDto registrationDto) {
        if (Objects.nonNull(registrationDto.getUserId()) || Objects.nonNull(registrationDto.getMobileNumber())) {
            UserDetails userDetails = userRepository.findByUserIdMobileNumberOrUserIdId(registrationDto.getMobileNumber(), registrationDto.getUserId());
            if (Objects.nonNull(userDetails)) {
                userDetails.setFirstName(registrationDto.getFirstName());
                userDetails.setLastName(registrationDto.getLastName());
                userDetails.setEmailId(registrationDto.getEmail());
                userDetails.getUserId().setMobileNumber(registrationDto.getMobileNumber());
                userDetails.setAddressLineFirst(registrationDto.getAddressLineFirst());
                userDetails.setAddressLineSecond(registrationDto.getAddressLineSecond());
                userDetails.setCity(registrationDto.getCity());
                userDetails.setState(registrationDto.getState());
                userDetails.setPinCode(registrationDto.getPinCode());
                userDetails.setRemarks(registrationDto.getRemarks());

                userRepository.save(userDetails);

                return userDetailDtoConvertor(userDetails);

            } else {
                throw new UnauthorizedException(new ErrorDetails(AppConstant.USER_NOT_FOUND, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION, "User not found."));
            }
        } else {
            throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "UserId or Mobile is not correct."));
        }
    }

    @Override
    @Transactional
    public void deleteUser(UserIdDto userIdDto) {
        if (Objects.nonNull(userIdDto.getUserId()) || Objects.nonNull(userIdDto.getMobileNumber())) {

            UserDetails userDetails = userRepository.findByUserIdMobileNumberOrUserIdId(userIdDto.getMobileNumber(), userIdDto.getUserId());
            if (Objects.nonNull(userDetails)) {
                userRepository.delete(userDetails);
            } else {
                throw new UnauthorizedException(new ErrorDetails(AppConstant.USER_NOT_FOUND, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION, "User not found."));
            }
        } else {
            throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "UserId or Mobile is not correct."));
        }
    }

    @Override
    @Transactional
    public void activateUser(UserIdDto userIdDto) {
        if (Objects.nonNull(userIdDto.getUserId()) || Objects.nonNull(userIdDto.getMobileNumber())) {

            UserDetails userDetails = userRepository.findByUserIdMobileNumberOrUserIdId(userIdDto.getMobileNumber(), userIdDto.getUserId());
            if (Objects.nonNull(userDetails)) {
                userDetails.setActive('Y');
                userRepository.save(userDetails);
            } else {
                throw new UnauthorizedException(new ErrorDetails(AppConstant.USER_NOT_FOUND, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION, "User not found."));
            }
        } else {
            throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "UserId or Mobile is not correct."));
        }
    }

    @Override
    @Transactional
    public void deactivateUser(UserIdDto userIdDto) {
        if (Objects.nonNull(userIdDto.getUserId()) || Objects.nonNull(userIdDto.getMobileNumber())) {

            UserDetails userDetails = userRepository.findByUserIdMobileNumberOrUserIdId(userIdDto.getMobileNumber(), userIdDto.getUserId());
            if (Objects.nonNull(userDetails)) {
                userDetails.setActive('N');
                userRepository.save(userDetails);
            } else {
                throw new UnauthorizedException(new ErrorDetails(AppConstant.USER_NOT_FOUND, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION, "User not found."));
            }
        } else {
            throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "UserId or Mobile is not correct."));
        }
    }

    *//*@Transactional
    @Override
    public void addBasicDetails(LoanDetailsDto loanDetailsDto) {
        if(Objects.nonNull(loanDetailsDto.getUserId())){
            BasicDetails basicDetails = basicDetailsRepository.findById(loanDetailsDto.getUserId()).orElse(null);
            if(Objects.isNull(basicDetails)) {
                basicDetails = new BasicDetails();
                basicDetails.setCreatedBy("Admin");
                basicDetails.setCreatedDate(new Date());
            }
            basicDetails.setUserId(loanDetailsDto.getUserId());
            basicDetails.setName(loanDetailsDto.getName());
            basicDetails.setDateOfBirth(loanDetailsDto.getDateOfBirth());
            basicDetails.setGender(loanDetailsDto.getGender());
            basicDetails.setQualification(loanDetailsDto.getQualification());
            basicDetails.setResOwner(loanDetailsDto.getResOwner());
            basicDetails.setYrsCa(loanDetailsDto.getYrsCa());
            basicDetails.setLrAmount(loanDetailsDto.getLrAmount());
            basicDetails.setPsyResult(loanDetailsDto.getPsyResult());
            basicDetails.setMobileNumber(loanDetailsDto.getMobileNumber());
            basicDetails.setPhone(loanDetailsDto.getPhone());
            basicDetails.setEmail(loanDetailsDto.getEmail());
            basicDetails.setStatus(loanDetailsDto.getStatus());
            basicDetails.setFirstName(loanDetailsDto.getFirstName());
            basicDetails.setLastName(loanDetailsDto.getLastName());
            basicDetails.setKycResp(loanDetailsDto.getKycResp());
            basicDetails.setPanNumber(loanDetailsDto.getPanNumber());


            basicDetailsRepository.save(basicDetails);
        }else{
            throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "Please enter userId."));
        }
    }

    @Override
    public LoanDetailsDto getBasicDetails(LoanDetailsDto loanDetailsDto) {

        if(Objects.nonNull(loanDetailsDto.getUserId())){
            BasicDetails basicDetails = basicDetailsRepository.findById(loanDetailsDto.getUserId()).Throw(() -> new InvalidInputException(new ErrorDetails(AppConstant.USER_NOT_FOUND, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "User not found.")));
            loanDetailsDto.setName(basicDetails.getName());
            loanDetailsDto.setDateOfBirth(basicDetails.getDateOfBirth());
            loanDetailsDto.setEmail(basicDetails.getEmail());
            loanDetailsDto.setGender(basicDetails.getGender());
            loanDetailsDto.setLrAmount(basicDetails.getLrAmount());
            loanDetailsDto.setPhone(basicDetails.getPhone());
            loanDetailsDto.setMobileNumber(basicDetails.getMobileNumber());
            loanDetailsDto.setQualification(basicDetails.getQualification());
            loanDetailsDto.setPsyResult(basicDetails.getPsyResult());
            loanDetailsDto.setStatus(basicDetails.getStatus());
            loanDetailsDto.setResOwner(basicDetails.getResOwner());
            loanDetailsDto.setTDate(basicDetails.gettDate());
            loanDetailsDto.setFirstName(basicDetails.getFirstName());
            loanDetailsDto.setLastName(basicDetails.getLastName());
            loanDetailsDto.setPanNumber(basicDetails.getPanNumber());
            loanDetailsDto.setKycResp(basicDetails.getKycResp());


        }else{
            throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "Please enter userId."));
        }
        return loanDetailsDto;
    }

    @Transactional
    @Override
    public void removeBasicDetails(LoanDetailsDto loanDetailsDto) {
        if(Objects.nonNull(loanDetailsDto.getUserId())){
            BasicDetails basicDetails = basicDetailsRepository.findById(loanDetailsDto.getUserId()).orElseThrow(() -> new InvalidInputException(new ErrorDetails(AppConstant.USER_NOT_FOUND, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "User not found.")));

            basicDetailsRepository.delete(basicDetails);

        }else{
            throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "Please enter userId."));
        }
    }*//*

    @Override
    @Transactional
    public void addContactUs(ContactUsDto contactUsDto) {

        if(Objects.nonNull(contactUsDto.getTxnDate()) && Objects.nonNull(contactUsDto.getPhone()) && Objects.nonNull(contactUsDto.getEmail())){
            ContactUs contactUs = new ContactUs();
            contactUs.setContactUsId(new ContactUsId(contactUsDto.getTxnDate(), contactUsDto.getEmail(), contactUsDto.getPhone()));
            contactUs.setCategory(contactUsDto.getCategory());
            contactUs.setName(contactUsDto.getName());
            contactUs.setRemarks(contactUsDto.getRemarks());
            contactUs.setCreatedBy("Admin");
            contactUs.setCreatedDate(new Date());

            contactUsRepository.save(contactUs);

        }else{
            throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "Please enter mandatory fields."));
        }
    }

    @Override
    public String uploadProfileImage(MultipartFile file, String userId) {

        if(Objects.nonNull(file) && Objects.nonNull(userId)) {
            UserDetails userDetails =  validateUser(userId);
            try {

                String finalFileName = userId + "_" + file.getOriginalFilename();
                BlobClient blob = blobContainerClient.getBlobClient(finalFileName);//file.getOriginalFilename());
                blob.upload(file.getInputStream(), file.getSize(), true);

                saveProfileImage(finalFileName, userDetails);
                return finalFileName;
            }catch(Exception e){
                e.printStackTrace();
                throw new BusinessException(new ErrorDetails(AppConstant.FILE_UPLOAD_ERROR, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION, "Error occurred while uploading profile image."));
            }

        }else{
            throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "Invalid input."));
        }
    }

    @Transactional
    private void saveProfileImage(String profileImageName, UserDetails userDetails) {
        userDetails.setProfileImageName(profileImageName);
        userRepository.save(userDetails);
    }

    @Override
    public Map<String, Object> downloadProfileImage(String userId) {
        Map<String, Object> res = new HashMap<>();
        final byte[] bytes;
        if(Objects.nonNull(userId)) {
            UserDetails userDetails =  validateUser(userId);
            String fileName = userDetails.getProfileImageName();
            if(Objects.isNull(fileName)){
                throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION, "Profile image not found."));
            }

            BlobClient blob = blobContainerClient.getBlobClient(fileName);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            blob.download(outputStream);
            bytes = outputStream.toByteArray();

            res.put("fileName", fileName);
            res.put("content", bytes);
        }else{
            throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "Invalid input."));
        }
        return res;
    }

    @Override
    public boolean removeProfileImage(String userId) {
        if(Objects.nonNull(userId)) {
            UserDetails userDetails =  validateUser(userId);
            String fileName = userDetails.getProfileImageName();

            if(Objects.isNull(fileName)){
                throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION, "Profile image not found."));
            }

            BlobClient blob = blobContainerClient.getBlobClient(fileName);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            blob.delete();
            saveProfileImage(null, userDetails);

            return true;
        }else{
            throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "Invalid input."));
        }
    }

    @Override
    public List<UserDetailDto> getAllUserDetails() {

        List<UserDetailDto> userDetailDtoList = new ArrayList<>();

        List<UserDetails> userDetailsList = userRepository.findAll();

        if(Objects.nonNull(userDetailsList) && !userDetailsList.isEmpty()){
            userDetailDtoList = userDetailsList.stream().map(userDetails -> {
                UserDetailDto userDetailDto = new UserDetailDto();
                userDetailDto.setUserId(userDetails.getUserId().getId());
                userDetailDto.setUserMode(userDetails.getUserMode());
                userDetailDto.setUserType(userDetails.getUserType());
                userDetailDto.setCreatedDate(userDetails.getCreatedDate());
                userDetailDto.setActive(userDetails.getActive() == 'Y' ? true : false);
                userDetailDto.setAdminFlag(userDetails.getAdminFlag() == 'Y' ? true : false);
                userDetailDto.setMobileNumber(userDetails.getUserId().getMobileNumber());
                userDetailDto.setCreatedBy(userDetails.getCreatedBy());
                userDetailDto.setEmailId(userDetails.getEmailId());
                userDetailDto.setFirstName(userDetails.getFirstName());
                userDetailDto.setLastName(userDetails.getLastName());
                userDetailDto.setAddressLineFirst(userDetails.getAddressLineFirst());
                userDetailDto.setAddressLineSecond(userDetails.getAddressLineSecond());
                userDetailDto.setState(userDetails.getState());
                userDetailDto.setCity(userDetails.getCity());
                userDetailDto.setPinCode(userDetails.getPinCode());
                userDetailDto.setRemarks(userDetails.getRemarks());

                return userDetailDto;

            }).collect(Collectors.toList());
        }

        return userDetailDtoList;
    }

    private String getLoanIdByUserId(String userId){
        CsrDetails csrDetails = csrDetailsRepository.findByIdUserId(userId);
        if(Objects.nonNull(csrDetails) && Objects.nonNull(csrDetails.getId().getLoanNumber())){
            return csrDetails.getId().getLoanNumber();
        }
        return null;
    }


    private UserDetails validateUser(String userId){
        UserDetails userDetails = userRepository.findByUserIdId(userId);
        if(Objects.isNull(userDetails)){
            throw new UnauthorizedException(new ErrorDetails(AppConstant.USER_NOT_FOUND, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "User not found."));
        }
        return userDetails;
    }*/
}
