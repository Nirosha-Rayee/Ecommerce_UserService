package org.example.ecommerce_userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ValidTokenRequestDto {
    private Long userId;
    private String token;

}
