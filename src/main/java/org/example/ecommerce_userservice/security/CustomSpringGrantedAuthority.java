package org.example.ecommerce_userservice.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ecommerce_userservice.models.Role;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@NoArgsConstructor

@JsonDeserialize(as = CustomSpringGrantedAuthority.class)

public class CustomSpringGrantedAuthority implements GrantedAuthority {
    private Role role;

    public CustomSpringGrantedAuthority(Role role) {
        this.role = role;
    }

    @Override
    @JsonIgnore
    public String getAuthority() {

        return role.getRole();
    }
}
