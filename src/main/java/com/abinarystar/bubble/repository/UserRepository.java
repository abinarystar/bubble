package com.abinarystar.bubble.repository;

import com.abinarystar.bubble.repository.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
