package org.example.ecommerce_userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginRequestDto {
    private String email;
    private String password;
}
