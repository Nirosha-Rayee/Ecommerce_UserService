package org.example.ecommerce_userservice.services;

/*import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.ecommerce_userservice.dtos.UserDto;
import org.example.ecommerce_userservice.models.Session;
import org.example.ecommerce_userservice.models.SessionStatus;
import org.example.ecommerce_userservice.models.User;
import org.example.ecommerce_userservice.repositories.SessionRepository;
import org.example.ecommerce_userservice.repositories.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
   // private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
       // this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<UserDto> login(String email, String password) {
        //1. we need to get the user from the database via userRepository
        Optional<User> userOptional = userRepository.findByEmail(email);
        //2. if user is does not exist
        if(userOptional.isEmpty()){
            return null;
        }
        //3. we got the user
        User user = userOptional.get();
//        bCryptPasswordEncoder.matches(password, user.getPassword());
//        if(user.getPassword().equals(password)){
//            return null;
//        } // or

        //4. validate the password
//        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
//            return null;
//        }


        //5. we are trying to generate a token using RandomStringUtils

        //String token = RandomStringUtils.randomAlphanumeric(30 ); //before using the JWT for token generation

//        String message = "{\n" +
//                // using jwt for token generation, and these are payload details of the token, we need a map of deatils,but it is in String format.
//        "   \"email\": \"naman@scaler.com\",\n" +
//        "   \"roles\": [\n" +
//        "      \"mentor\",\n" +
//        "      \"ta\"\n" +
//        "   ],\n" +
//        "   \"expirationDate\": \"23rdOctober2023\"\n" +
//        "}";
//        // user_id
//        // user_email
//        // roles
//        byte[] content = message.getBytes(StandardCharsets.UTF_8); //so, comment out and create a map of details

        Map<String, Object> jsonForJwt = new HashMap<>();
        jsonForJwt.put("email", user.getEmail());
        jsonForJwt.put("roles", user.getRoles());
        jsonForJwt.put("expirationDate", new Date());
        //if(xx =!null)
        jsonForJwt.put("createdAt" , new Date());


        //String token = Jwts.builder().content(content).compact(); // we have generated the token but 3rd part is empty,i.e, signature,put comment out for now

        //we need to add the signature to the token, like this below 1,2,3 lines

        MacAlgorithm alg = Jwts.SIG.HS256; //1
        SecretKey key = alg.key().build();//2

      //  String token = Jwts.builder().content(content).signWith(key, alg).compact();//3

        String token = Jwts.builder().claims(jsonForJwt).signWith(key, alg).compact();




        // 6. after generating the token, we have created the session object and saved it to the database
        Session session = new Session();

        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUser(user);
        session.setToken(token);
        sessionRepository.save(session);

        // 7. we have to return a response of userDto (response entity with the userDto)
        //UserDto userDto = new UserDto(); // instead of this, we have to write conversion logic from user to userDto. has to write in User class
        UserDto userDto = UserDto.from(user); // we got the userDto from the user

        //Map<String, String> headers = new HashMap<>();
        //headers.put(HttpHeaders.SET_COOKIE,   token);

        //8. we have putted the token in the header
        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token :" +token);

        //9. we have to return the response entity with the userDto and headers
        ResponseEntity<UserDto> response = new ResponseEntity<>(UserDto.from(user), headers, HttpStatus.OK);
        //response.getHeaders().add(HttpHeaders.SET_COOKIE,token);

        return response;

        //return ResponseEntity.ok(UserDto.from(user));
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
       // user.setPassword(bCryptPasswordEncoder.encode(password)); //to hash the password
        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);

    }

    public SessionStatus validateToken (String token, Long userId) {

        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);
        if(sessionOptional.isEmpty()){
            return null;
        }
       // Session session = sessionOptional.get();

        MacAlgorithm alg = Jwts.SIG.HS256;
        SecretKey key = alg.key().build();

        //verify the token with the key
        Claims claims =
                Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

//        if(exiprytime > currentdate) { // i can put more details here to check the token like expiry time,check for roles, etc
//
//        }
        // check login device

        //return session.getSessionStatus();
        return SessionStatus.ACTIVE;

    }



}*/

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.example.ecommerce_userservice.clients.KafkaProducerClient;
import org.example.ecommerce_userservice.dtos.SendEmailMessageDto;
import org.example.ecommerce_userservice.dtos.UserDto;
import org.example.ecommerce_userservice.models.Session;
import org.example.ecommerce_userservice.models.SessionStatus;
import org.example.ecommerce_userservice.models.User;
import org.example.ecommerce_userservice.repositories.SessionRepository;
import org.example.ecommerce_userservice.repositories.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class  AuthService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    //private BCryptPasswordEncoder bCryptPasswordEncoder;
    private KafkaProducerClient kafkaProducerClient;
    private ObjectMapper objectMapper;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository,
                       KafkaProducerClient kafkaProducerClient, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        //this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.kafkaProducerClient = kafkaProducerClient;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<UserDto> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        //User does not exist
        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();

        //Validation
//        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
//            return null;
//        }

        //String token = RandomStringUtils.randomAlphanumeric(30);

//        String message = "{\n" +
//        "   \"email\": \"naman@scaler.com\",\n" +
//        "   \"roles\": [\n" +
//        "      \"mentor\",\n" +
//        "      \"ta\"\n" +
//        "   ],\n" +
//        "   \"expirationDate\": \"23rdOctober2023\"\n" +
//        "}";
//        // user_id
//        // user_email
//        // roles
//        byte[] content = message.getBytes(StandardCharsets.UTF_8);

        Map<String, Object> jsonForJwt = new HashMap<>();
        jsonForJwt.put("email", user.getEmail());
        jsonForJwt.put("roles", user.getRoles());
        jsonForJwt.put("expirationDate", new Date());
        //if(xx =!null)
        jsonForJwt.put("createdAt" , new Date());

        MacAlgorithm alg = Jwts.SIG.HS256;
        SecretKey key = alg.key().build();

        String token = Jwts.builder().claims(jsonForJwt).signWith(key, alg).compact();

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        UserDto userDto = User.from(user);

//        Map<String, String> headers = new HashMap<>();
//        headers.put(HttpHeaders.SET_COOKIE, token);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);
//        response.getHeaders().add(HttpHeaders.SET_COOKIE, token);

        return response;
    }

    public ResponseEntity<Void> logout(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return null;
        }

        Session session = sessionOptional.get();

        session.setSessionStatus(SessionStatus.ENDED);

        sessionRepository.save(session);

        return ResponseEntity.ok().build();
    }

    public UserDto signUp(String email, String password) {
        User user = new User();
        user.setEmail(email);
        //user.setPassword(bCryptPasswordEncoder.encode(password));

        User savedUser = userRepository.save(user);

        UserDto userDto = UserDto.from(savedUser); //but needs to send json representation of the userdto. so, we need to convert it to string using class called objectmapper

        try{
        kafkaProducerClient.sendMessage("userSignUp", objectMapper.writeValueAsString(userDto));

        SendEmailMessageDto emailMessage = new SendEmailMessageDto();
        emailMessage.setTo(userDto.getEmail());
        emailMessage.setFrom("admin@scaler.com");
        emailMessage.setSubject("Welcome to Scaler");
        emailMessage.setBody("Thanks for creating an account. We look forward to you growing. Team Scaler");
        kafkaProducerClient.sendMessage("sendEmail", objectMapper.writeValueAsString(emailMessage));



        } catch (Exception e) {
            System.out.println("Something went wrong while sending message to kafka");
        }

        //return UserDto.from(savedUser);
        return userDto;
    }

    public SessionStatus validate(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return null;
        }
        MacAlgorithm alg = Jwts.SIG.HS256;
        SecretKey key = alg.key().build();

        Claims claims =
                Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

//        if(exiprytime > currentdate) {
//
//        }
        // check login device

        return SessionStatus.ACTIVE;
    }

}
// we have to send the email to user in this format. so, we need a dto for this ,SendEmailMessageDto.
// {
//   to: "",
//   from: "",
//   subject: "",
//   body: ""
// }
