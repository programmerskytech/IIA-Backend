package com.astro.service.impl;

import com.astro.constant.AppConstant;
import com.astro.dto.workflow.RoleDto;
import com.astro.dto.workflow.RoleRequestDto;

import com.astro.entity.RoleMaster;

import com.astro.exception.BusinessException;
import com.astro.exception.ErrorDetails;
import com.astro.repository.RoleMasterRepository;

import com.astro.service.RoleMasterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleMasterServiceImpl implements RoleMasterService {

    @Autowired
    private RoleMasterRepository roleMasterRepository;
    @Override
    public RoleDto createRole(RoleRequestDto roleDto) {
       RoleMaster roleMaster = new RoleMaster();
       roleMaster.setRoleName(roleDto.getRoleName());
       roleMaster.setCreatedBy(roleDto.getCreatedBy());
       roleMasterRepository.save(roleMaster);
        return mapToResponseDTO(roleMaster);

    }


    @Override
    public RoleDto updateRole(int roleId, RoleRequestDto roleDto) {

       RoleMaster roleMaster = roleMasterRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "Role not found for the provided role ID.")
                ));

       roleMaster.setRoleName(roleDto.getRoleName());
       roleMaster.setCreatedBy(roleDto.getCreatedBy());
       roleMasterRepository.save(roleMaster);
        return mapToResponseDTO(roleMaster);
    }

    @Override
    public List<RoleDto> getAllRoles() {
          List<RoleMaster> roleMasters = roleMasterRepository.findAll();
        return roleMasters.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public RoleDto getRoleById(int roleId) {
       RoleMaster roleMaster = roleMasterRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "role not found for the provided role ID.")
                ));
        return mapToResponseDTO(roleMaster);
    }

    @Override
    public void deleteRole(int roleId) {
        RoleMaster roleMaster = roleMasterRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "role not found for the provided ID."
                        )
                ));
        try {
           roleMasterRepository.delete(roleMaster);
        } catch (Exception ex) {
            throw new BusinessException(
                    new ErrorDetails(
                            AppConstant.INTER_SERVER_ERROR,
                            AppConstant.ERROR_TYPE_CODE_INTERNAL,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "An error occurred while deleting the role."
                    ),
                    ex
            );
        }
    }

    private RoleDto mapToResponseDTO(RoleMaster roleMaster) {

        RoleDto dto = new RoleDto();
        dto.setRoleId(roleMaster.getRoleId());
        dto.setRoleName(roleMaster.getRoleName());
        dto.setCreatedBy(roleMaster.getCreatedBy());
        dto.setCreatedDate(roleMaster.getCreatedDate());
        return dto;
    }

}
