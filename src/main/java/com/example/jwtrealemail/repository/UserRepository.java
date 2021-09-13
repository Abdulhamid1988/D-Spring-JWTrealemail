package com.example.jwtrealemail.repository;

import com.example.jwtrealemail.entity.User;
import org.hibernate.type.UUIDBinaryType;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
}
