package com.supelpawel.springbootmoviesproject.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.supelpawel.springbootmoviesproject.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);
}
