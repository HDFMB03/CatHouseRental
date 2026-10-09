package com.Cathouse.project.DataTransferObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryDto extends QueryDto {
    private String account;
    private String username;
    private String email;
    private Integer role;
    private Boolean isLogin;
    private Boolean isWord;
}
