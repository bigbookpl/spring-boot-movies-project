package com.supelpawel.springbootmoviesproject.user.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentUser extends User {

    private final com.supelpawel.springbootmoviesproject.user.model.User user;

    public CurrentUser(String username, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       com.supelpawel.springbootmoviesproject.user.model.User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public com.supelpawel.springbootmoviesproject.user.model.User getUser() {
        return user;
    }
}
