package com.javamentor.qa.platform.security;

import com.javamentor.qa.platform.converters.JwtConverter;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JwtUserDetailsService implements UserDetailsService {
    private final UserDao userDao;
    private final JwtConverter jwtConverter;

    public JwtUserDetailsService(UserDao userDao, JwtConverter jwtConverter) {
        this.userDao = userDao;
        this.jwtConverter = jwtConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return jwtConverter.jwtUserByUser(userDao
                .getByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User не найден")));
    }
}
