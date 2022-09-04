package com.supelpawel.springbooksproject.user.service;

import com.supelpawel.springbooksproject.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findByUserName(String username);

    void saveUser(User user);

    String saveUsers();

    Optional<User> findById(Long id);

    List<User> findAll();

    void update(User user);
}
