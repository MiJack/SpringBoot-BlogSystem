package com.mijack.sbbs.auth.provider;

import com.mijack.sbbs.auth.token.RestfulApiToken;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.service.AuthService;
import com.mijack.sbbs.service.UserService;
import com.mijack.sbbs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author Mr.Yuan
 */
@Component
public class RestfulApiAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RestfulApiToken restfulApiToken = (RestfulApiToken) authentication;
        Assert.notNull(restfulApiToken, "restful api must be not null");
        String username = restfulApiToken.getUsername();
        String email = restfulApiToken.getEmail();
        String password = restfulApiToken.getPassword();
        String token = restfulApiToken.getToken();
        User user = !Utils.isEmpty(token) ? authService.findUserByToken(token)
                : userService.login(username, email, password);
        restfulApiToken.setAuthenticated(user != null);
        restfulApiToken.setUser(user);
        SecurityContextHolder.getContext().setAuthentication(restfulApiToken);
        return restfulApiToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RestfulApiToken.class.isAssignableFrom(authentication);
    }
}
