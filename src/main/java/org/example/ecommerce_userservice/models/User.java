package org.example.ecommerce_userservice.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.example.ecommerce_userservice.dtos.UserDto;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Entity
@JsonDeserialize(as = User.class)

public class User extends BaseModel{
    private String email;
    private String password;
    @ManyToMany
    private Set<Role> roles = new HashSet<>();


    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
