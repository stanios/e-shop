package com.finalproject.cf.controller;

import com.finalproject.cf.dto.UserDTO;
import com.finalproject.cf.entity.User;
import com.finalproject.cf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRolesAndUsers() {
        userService.initRolesAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public ResponseEntity<UserDTO> registerNewUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.registerUser(userDTO);
        UserDTO dto = map(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




    private UserDTO map(User user) {
        return new UserDTO(
                user.getUserName(),
                user.getUserFirstname(),
                user.getUserLastname(),
                user.getUserPassword(),
                user.getRole()
        );
    }
}
