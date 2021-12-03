package com.example.officebuilding.security.service;

import com.example.officebuilding.security.entities.Role;
import com.example.officebuilding.service.IGeneralService;

public interface IRoleService extends IGeneralService<Role> {
    Role findByName(String name);
}