package com.finalproject.cf.entity;

import lombok.Data;

@Data
public class JwtRequest {

    private String userName;
    private String userPassword;
}
