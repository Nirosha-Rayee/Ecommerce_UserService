package org.example.ecommerce_userservice.controllers;

import org.example.ecommerce_userservice.dtos.*;
import org.example.ecommerce_userservice.models.SessionStatus;
import org.example.ecommerce_userservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) {
//        UserDto userDto = authService.login(request.getEmail(), request.getPassword());
//        return new ResponseEntity<>(userDto, HttpStatus.OK); // or we can write the below line

        return authService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request) {
        return authService.logout(request.getToken(), request.getUserId());



    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto request) {
        UserDto userDto = authService.signUp(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);

        //return authService.signup(request.getEmail(), request.getPassword());
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(@RequestBody ValidTokenRequestDto request) {

        SessionStatus sessionStatus = authService.validate(request.getToken(), request.getUserId());
        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
       //return authService.validateToken(request.getToken(), request.getUserId()
    }


}
