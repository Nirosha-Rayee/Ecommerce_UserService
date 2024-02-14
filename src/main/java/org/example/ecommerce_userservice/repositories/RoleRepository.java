package org.example.ecommerce_userservice.repositories;

import org.example.ecommerce_userservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByIdIn(List<Long> roleIds);

}
