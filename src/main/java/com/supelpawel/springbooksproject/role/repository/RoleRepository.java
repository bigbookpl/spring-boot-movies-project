package com.supelpawel.springbooksproject.role.repository;

import com.supelpawel.springbooksproject.role.data.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
