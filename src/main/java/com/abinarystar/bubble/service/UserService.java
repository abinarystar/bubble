package com.abinarystar.bubble.service;

import com.abinarystar.bubble.repository.UserRepository;
import com.abinarystar.bubble.repository.model.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public User save(User user) {
    return userRepository.save(user);
  }

  @Transactional
  public void delete(User user) {
    user.setIsDeleted(Boolean.TRUE);
    userRepository.save(user);
  }

  public Optional<User> getById(String id) {
    Optional<User> user = userRepository.findById(id);
    user.ifPresent(u -> Hibernate.initialize(u.getEmails()));
    return user;
  }
}
