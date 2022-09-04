package com.supelpawel.springbooksproject.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.supelpawel.springbooksproject.user.data.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
