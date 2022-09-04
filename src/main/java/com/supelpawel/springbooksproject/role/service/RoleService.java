package com.supelpawel.springbooksproject.role.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.supelpawel.springbooksproject.role.data.Role;
import com.supelpawel.springbooksproject.role.repository.RoleRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
