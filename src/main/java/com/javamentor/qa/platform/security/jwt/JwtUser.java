package com.javamentor.qa.platform.security.jwt;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

public class JwtUser implements UserDetails {
    private final Long id;
    private final String email;
    private final String fullName;
    private final String password;
    private final Boolean enabled;
    private final LocalDateTime lastUpdateDateTime;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(Long id, String email, String fullName, String password, Boolean enabled, LocalDateTime lastUpdateDateTime, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.enabled = enabled;
        this.lastUpdateDateTime = lastUpdateDateTime;
        this.authorities = authorities;
    }


    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    public String getFullName() {
        return fullName;
    }

    @JsonIgnore
    public LocalDateTime getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }


    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
