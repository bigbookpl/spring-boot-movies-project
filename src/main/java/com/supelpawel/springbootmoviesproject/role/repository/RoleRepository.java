package com.supelpawel.springbootmoviesproject.role.repository;

import com.supelpawel.springbootmoviesproject.role.data.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByName(String name);
}
