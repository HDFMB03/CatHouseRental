package com.Cathouse.project.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponseVO {
    private Integer id;
    private String token;
    private Integer role;
}
