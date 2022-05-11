package com.example.demo.Repositories;

import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

   User findByUsername(String username);

   User findByEmail(String email);

   User findByPhoneNumber(String phoneNumber);

    User findByActivationCode(String code);

    User findByResetPasswordToken(String token);
}
