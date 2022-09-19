package com.supelpawel.springbootmoviesproject.user.service;

import com.supelpawel.springbootmoviesproject.role.data.Role;
import com.supelpawel.springbootmoviesproject.role.repository.RoleRepository;
import com.supelpawel.springbootmoviesproject.user.model.User;
import com.supelpawel.springbootmoviesproject.user.repository.UserRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public String showAddUserForm(Model model) {
    User user = new User();

    model.addAttribute("user", user);
    return "user/add";
  }

  @Override
  @Transactional
  public String processAddUserForm(User user, BindingResult result) {
    if (result.hasErrors()) {
      return "user/add";
    } else if (findByUserName(user.getUsername()) != null) {
      return "user/warning";
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setEnabled(true);
    Role userRole = roleRepository.findByName("ROLE_USER");
    user.setRoles(new HashSet<>(Arrays.asList(userRole)));
    userRepository.save(user);
    return "redirect:/login";
  }

  @Override
  public User findByUserName(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  @Transactional
  public void update(User user) {
    userRepository.save(user);
  }
}

