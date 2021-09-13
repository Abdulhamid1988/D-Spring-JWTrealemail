package com.example.jwtrealemail.repository;

import com.example.jwtrealemail.entity.Role;
import com.example.jwtrealemail.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(RoleName roleName);
}
