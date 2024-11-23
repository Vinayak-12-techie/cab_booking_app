package com.vinayak.project.uber.uberApp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginRequestDto {
    private String email;
    private String password;
}
