package com.mijack.sbbs.service;

import com.mijack.sbbs.model.User;

/**
 * @author Mr.Yuan
 */
public interface UserService {

    User login(String username, String email, String password);

    User findUser(long userId);

    User createUser(String name, String email, String password);

    User updatePassword(User user, String oldPassword, String newPassword);

    User updateEmail(User user, String email);

    User updateUsername(User user, String username);
}
