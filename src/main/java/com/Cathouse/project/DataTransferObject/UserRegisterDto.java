package com.Cathouse.project.DataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    private String username;
    private String account;
    private String password;
    private String email;
    private String avatar;
    private String phone;
    private Integer gender;
    private LocalDate birthday;
}
