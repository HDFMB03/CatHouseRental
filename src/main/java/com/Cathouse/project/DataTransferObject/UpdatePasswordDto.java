package com.Cathouse.project.DataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePasswordDto {
    private String account;
    private String password;
}
