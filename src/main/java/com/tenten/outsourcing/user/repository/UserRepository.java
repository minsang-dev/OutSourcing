package com.tenten.outsourcing.user.repository;

import com.tenten.outsourcing.user.entity.User;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(@NotBlank(message = "이메일은 필수값 입니다.") String email);

    boolean existsByEmail(String email);
}
