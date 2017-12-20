package com.mijack.sbbs.service;

import com.mijack.sbbs.model.Token;
import com.mijack.sbbs.model.User;

/**
 * @author Mr.Yuan
 */
public interface AuthService {
    User findUserByToken(String token);


    String createToken(User user);
}
