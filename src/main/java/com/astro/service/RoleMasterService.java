package com.astro.service;

import com.astro.dto.workflow.RoleDto;
import com.astro.dto.workflow.RoleRequestDto;


import java.util.List;

public interface RoleMasterService {

    public RoleDto createRole(RoleRequestDto roleDto);
    public RoleDto updateRole(int roleId, RoleRequestDto roleDto);
    public List<RoleDto> getAllRoles();
    public RoleDto getRoleById(int roleId);

    public void deleteRole(int roleId);

}
