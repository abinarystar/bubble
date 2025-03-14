package com.abinarystar.bubble.repository;

import com.abinarystar.bubble.repository.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {
}
