package com.example.Makeup.service;

import com.example.Makeup.entity.Role;
import com.example.Makeup.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> getRole(Integer id){
        return roleRepository.findById(id);
    }
}
