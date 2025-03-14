package com.abinarystar.bubble.repository;

import com.abinarystar.bubble.repository.model.UserEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEmailRepository extends JpaRepository<UserEmail, String> {
}
