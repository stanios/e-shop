package com.finalproject.cf.service;

import com.finalproject.cf.dto.UserDTO;
import com.finalproject.cf.entity.Role;
import com.finalproject.cf.entity.User;
import com.finalproject.cf.repo.RoleRepository;
import com.finalproject.cf.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserDTO dto) {
        User user = map(dto);

        Role role = roleRepository.findByRoleName("User");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);
        user.setUserPassword( getEncodedPassword(user.getUserPassword()));
        return userRepository.save(user);
    }

    private User map (UserDTO dto) {
        return new User(
                dto.getUserName(),
                dto.getUserFirstname(),
                dto.getUserLastname(),
                dto.getUserPassword(),
                dto.getRole()
        );
    }

    public void initRolesAndUser() {
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("User role");
        roleRepository.save(userRole);

        User adminUser = new User();
        adminUser.setUserFirstname("admin");
        adminUser.setUserLastname("admin");
        adminUser.setUserName("admin");
        adminUser.setUserPassword(getEncodedPassword("pass123"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);

        User user = new User();
        user.setUserFirstname("geo");
        user.setUserLastname("stan");
        user.setUserName("stan123");
        user.setUserPassword(getEncodedPassword("123"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        userRepository.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
