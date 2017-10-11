package com.mijack.sbbs.service.impl;

import com.mijack.sbbs.model.User;
import com.mijack.sbbs.repository.UserRepository;
import com.mijack.sbbs.service.UserService;
import com.mijack.sbbs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User login(String username, String email, String password) {
        User user = userRepository.findByUsernameOrEmail(username, email);
        if (user == null) {
            return null;
        }
        return Utils.isEquals(password, user.getPassword()) ?
                user : null;
    }

    @Override
    public User findUser(long userId) {
        return userRepository.findOne(userId);
    }
}
