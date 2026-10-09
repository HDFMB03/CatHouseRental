package com.Cathouse.project.DataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private String account;
    private String username;
    private String password;
    private String avatar;
    private String email;
    private Integer gender;
    private LocalDate birthday;
    private String phone;
}
