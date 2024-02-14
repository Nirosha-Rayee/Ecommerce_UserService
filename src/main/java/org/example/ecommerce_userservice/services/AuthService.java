package org.example.ecommerce_userservice.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.ecommerce_userservice.dtos.UserDto;
import org.example.ecommerce_userservice.models.Session;
import org.example.ecommerce_userservice.models.SessionStatus;
import org.example.ecommerce_userservice.models.User;
import org.example.ecommerce_userservice.repositories.SessionRepository;
import org.example.ecommerce_userservice.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<UserDto> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            return null;
        }
        User user = userOptional.get();
//        bCryptPasswordEncoder.matches(password, user.getPassword());
//        if(user.getPassword().equals(password)){
//            return null;
//        } // or
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            return null;
        }

        String token = RandomStringUtils.randomAlphanumeric(30 );
        Session session = new Session();

        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUser(user);
        session.setToken(token);
        sessionRepository.save(session);
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
        //user.setPassword(password);
        user.setPassword(bCryptPasswordEncoder.encode(password)); //to hash the password
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
