package com.mijack.sbbs.service.impl;

import com.mijack.sbbs.model.Token;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.repository.TokenRepository;
import com.mijack.sbbs.service.AuthService;
import com.mijack.sbbs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    TokenRepository tokenRepository;

    @Override
    public User findUserByToken(String tokenString) {
        Token token = tokenRepository.findByToken(tokenString);
        return token != null ? token.getUser() : null;
    }

    @Override
    public String createToken(User user) {
        // token 组成: base64(user_id:email:login_time)
        String userToken = Utils.base64Encode(user.getId() + "" + user.getEmail() + "" + System.currentTimeMillis());
        Token token = tokenRepository.findByUser(user);
        if (token == null) {
            token = new Token();
            token.setUser(user);
        }
        token.setToken(userToken);
        tokenRepository.save(token);
        return userToken;
    }
}
