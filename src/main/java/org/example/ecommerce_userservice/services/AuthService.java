package org.example.ecommerce_userservice.services;

import org.example.ecommerce_userservice.dtos.UserDto;
import org.example.ecommerce_userservice.models.Session;
import org.example.ecommerce_userservice.models.SessionStatus;
import org.example.ecommerce_userservice.models.User;
import org.example.ecommerce_userservice.repositories.SessionRepository;
import org.example.ecommerce_userservice.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public ResponseEntity<UserDto> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            return null;
        }
        User user = userOptional.get();
        if(user.getPassword().equals(password)){
            return null;
        }
        return ResponseEntity.ok(UserDto.from(user));
    }

    public ResponseEntity<Void> logout(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);
        if(sessionOptional.isEmpty()){
            return null;
        }
        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
        return ResponseEntity.ok().build();

    }

    public UserDto signup(String email, String password) {

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);

    }

    public SessionStatus validateToken (String token, Long userId) {

        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);
        if(sessionOptional.isEmpty()){
            return null;
        }
        Session session = sessionOptional.get();

        //return session.getSessionStatus();
        return SessionStatus.ACTIVE;

    }



}
