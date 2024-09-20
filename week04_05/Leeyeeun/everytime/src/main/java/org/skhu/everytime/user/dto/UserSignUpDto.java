package org.skhu.everytime.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSignUpDto {

    private String email;
    private String password;
    private String checkPassword;
    private String nickname;
    private String name;
    private String universityName;
    private int year;
}
