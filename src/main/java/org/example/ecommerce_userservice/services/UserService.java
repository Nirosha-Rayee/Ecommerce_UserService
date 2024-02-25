package org.example.ecommerce_userservice.services;

import org.example.ecommerce_userservice.dtos.UserDto;
import org.example.ecommerce_userservice.models.Role;
import org.example.ecommerce_userservice.models.User;
import org.example.ecommerce_userservice.repositories.RoleRepository;
import org.example.ecommerce_userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDto getUserDetails(Long userId) {
        System.out.println("I got a request");

        return new UserDto();

//        Optional<User> userOptional = userRepository.findById(userId);
//        if(userOptional.isEmpty()){
//            return null;
//        }
//        return UserDto.from(userOptional.get()); //comment out for to get call from product service

    }

    public UserDto setUserRoles(Long userId, List<Long> roleIds) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            return null;
        }
        User user = userOptional.get();

        List<Role> roles = roleRepository.findByIdIn(roleIds);
        user.setRoles(Set.copyOf(roles));

        User savedUser = userRepository.save(user); // userRepository.save(user);

        return UserDto.from(savedUser); //return UserDto.from(user);



    }
}
