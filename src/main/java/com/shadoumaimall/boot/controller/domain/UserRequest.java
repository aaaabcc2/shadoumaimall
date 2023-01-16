package com.shadoumaimall.boot.controller.domain;

import lombok.Data;

@Data
public class UserRequest {
    private String account;
    private String password;
    private String phone;
    private String email;
    private String emailCode;
}
