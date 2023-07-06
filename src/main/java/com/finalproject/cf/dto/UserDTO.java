package com.finalproject.cf.dto;

import com.finalproject.cf.entity.Role;
import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {


    @Size(min = 3, message = "UserName must have at least ${min} characters")
    private String userName;
    @Size(min = 3, message = "First name must have at least ${min} characters")
    private String userFirstname;
    @Size(min = 3, message = "Last name must have at least ${min} characters")
    private String userLastname;
    @Size(min = 8, message = "Password must have at least ${min} characters")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?\\d).*$")
    private String userPassword;
    private Set<Role> role;
}
