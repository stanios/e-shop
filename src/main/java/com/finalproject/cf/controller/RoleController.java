package com.finalproject.cf.controller;

import com.finalproject.cf.dto.RoleDTO;
import com.finalproject.cf.entity.Role;
import com.finalproject.cf.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @PostMapping({"/createNewRole"})
    public ResponseEntity<RoleDTO> createNewRole(@RequestBody RoleDTO roleDTO) {
        Role r = roleService.createNewRole(roleDTO);
        RoleDTO dto = map(r);
        return new  ResponseEntity<>(dto, HttpStatus.OK);
    }

    private RoleDTO map(Role role) {
        return new RoleDTO(
                role.getRoleName(),
                role.getRoleDescription()
        );
    }
}
