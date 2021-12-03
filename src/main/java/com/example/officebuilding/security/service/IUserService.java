package com.example.officebuilding.security.service;

import com.example.officebuilding.security.entities.User;
import com.example.officebuilding.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    Optional<User> findByUsername(String username);
}
