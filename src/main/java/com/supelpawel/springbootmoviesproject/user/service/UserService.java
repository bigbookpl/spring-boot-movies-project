package com.supelpawel.springbootmoviesproject.user.service;

import com.supelpawel.springbootmoviesproject.user.model.User;

import java.util.List;
import java.util.Optional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface UserService {

  User findByUserName(String username);

  String showAddUserForm(Model model);

  String processAddUserForm(User user, BindingResult result);

  Optional<User> findById(Long id);

  List<User> findAll();

  void update(User user);
}
