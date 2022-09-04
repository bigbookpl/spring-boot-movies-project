package com.supelpawel.springbooksproject.user.service;

import com.supelpawel.springbooksproject.role.data.Role;
import com.supelpawel.springbooksproject.role.repository.RoleRepository;
import com.supelpawel.springbooksproject.user.data.User;
import com.supelpawel.springbooksproject.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String saveUsers() {

        if (findAll().size() == 0) {

            User user1 = new User("user1", "user1");
            User user2 = new User("user2", "user2");
            User user3 = new User("user3", "user3");

            saveUser(user1);
            saveUser(user2);
            saveUser(user3);

            log.info("Users added to the database");

        } else {

            log.info("Users already in the database");
        }
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

    @Override
    @Transactional
    public void saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));

        userRepository.save(user);
    }
}

