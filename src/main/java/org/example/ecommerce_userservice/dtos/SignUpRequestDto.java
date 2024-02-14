package org.example.ecommerce_userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SignUpRequestDto {
    private String email;
    private String password;
}
