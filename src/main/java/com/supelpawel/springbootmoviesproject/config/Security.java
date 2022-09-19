package com.supelpawel.springbootmoviesproject.config;

import com.supelpawel.springbootmoviesproject.movie.repository.MovieRepository;
import com.supelpawel.springbootmoviesproject.role.repository.RoleRepository;
import com.supelpawel.springbootmoviesproject.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, RoleRepository.class,
    MovieRepository.class})
public class Security {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests((requests) -> requests
            .antMatchers("/").permitAll()
            .antMatchers("/movie/**").authenticated()
        )
        .formLogin((form) -> form
            .loginPage("/login")
            .defaultSuccessUrl("/movie/search")
            .permitAll()
        )
        .logout((logout) -> logout
            .logoutSuccessUrl("/")
            .permitAll()
        )
        .csrf().disable();

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}