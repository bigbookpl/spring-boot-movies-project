package com.supelpawel.springbootmoviesproject.user.controller;

import com.supelpawel.springbootmoviesproject.role.data.Role;
import com.supelpawel.springbootmoviesproject.role.service.RoleService;
import com.supelpawel.springbootmoviesproject.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;

@Controller
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @ModelAttribute("roles")
    Collection<Role> findAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/create-users")
    public String createUsers() {

        return userService.saveUsers();
    }
}