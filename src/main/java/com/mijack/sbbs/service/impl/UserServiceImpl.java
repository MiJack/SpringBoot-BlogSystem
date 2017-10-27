package com.mijack.sbbs.service.impl;

import com.google.common.collect.Sets;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.repository.RoleRepository;
import com.mijack.sbbs.repository.UserRepository;
import com.mijack.sbbs.service.UserService;
import com.mijack.sbbs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

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

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User createUser(String username, String email, String password) {
        User user = userRepository.findByUsernameOrEmail(username, email);
        if (user != null) {
            return null;
        }

        user = new User(username, password, email);
        user.setRoles(Sets.newHashSet(roleRepository.findByName(RoleRepository.ROLE_USER)));
        return userRepository.save(user);
    }
}
