package com.mijack.sbbs.auth.token;

import com.mijack.sbbs.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Mr.Yuan
 */
public class RestfulApiToken extends AbstractAuthenticationToken {
    private String username;
    private String email;
    private String password;
    private User user;
    private String token;
    /**
     * Creates a token with the supplied array of authorities.
     *
     */
    public RestfulApiToken() {
        super(Collections.emptyList());
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public User getPrincipal() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return (Collection<GrantedAuthority>) user.getAuthorities();
    }
}
