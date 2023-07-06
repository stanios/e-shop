package com.finalproject.cf.service;

import com.finalproject.cf.dto.RoleDTO;
import com.finalproject.cf.entity.Role;
import com.finalproject.cf.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    public Role createNewRole(RoleDTO roleDTO) {
        Role r = map(roleDTO);
        return roleRepository.save(r);
    }

    private Role map(RoleDTO roleDTO) {
        return new Role(
                roleDTO.getRoleName(),
                roleDTO.getRoleDescription()
        );
    }
}
