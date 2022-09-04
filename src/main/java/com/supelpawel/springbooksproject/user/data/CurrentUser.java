package com.supelpawel.springbooksproject.user.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentUser extends User {

    private final com.supelpawel.springbooksproject.user.data.User user;

    public CurrentUser(String username, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       com.supelpawel.springbooksproject.user.data.User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public com.supelpawel.springbooksproject.user.data.User getUser() {
        return user;
    }
}
