package com.mijack.sbbs.auth.filter;

import com.mijack.sbbs.auth.token.RestfulApiToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Mr.Yuan
 */
public class RestfulApiAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    public RestfulApiAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/api/**"));
        setContinueChainBeforeSuccessfulAuthentication(true);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        //获取host信息
        RestfulApiToken restfulApiToken = new RestfulApiToken();
        String token = request.getHeader("token");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        restfulApiToken.setToken(token);
        restfulApiToken.setUsername(username);
        restfulApiToken.setEmail(email);
        restfulApiToken.setPassword(password);
        //交给内部的AuthenticationManager去认证，实现解耦
        return getAuthenticationManager().authenticate(restfulApiToken);
    }
}
