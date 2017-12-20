package com.mijack.sbbs.service.impl;

import com.google.common.collect.Sets;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.repository.RoleRepository;
import com.mijack.sbbs.repository.UserRepository;
import com.mijack.sbbs.service.UserService;
import com.mijack.sbbs.utils.AssertUtils;
import com.mijack.sbbs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Yuan
 */
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
    public User createUser(String username, String email, String password) {
        User user = userRepository.findByUsernameOrEmail(username, email);
        if (user != null) {
            return null;
        }

        user = new User(username, password, email);
        user.setRoles(Sets.newHashSet(roleRepository.findByName(RoleRepository.ROLE_USER)));
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user, String oldPassword, String newPassword) {
        if (user == null || oldPassword == null || newPassword == null) {
            return user;
        }
        User oldUser = userRepository.findOne(user.getId());
        if (!oldUser.getPassword().equals(oldPassword)) {
            return user;
        }
        oldUser.setPassword(newPassword);
        return userRepository.save(oldUser);
    }

    @Override
    public User updateEmail(User user, String email) {
        if (user == null || email == null) {
            return user;
        }
        User oldUser = userRepository.findOne(user.getId());
        oldUser.setEmail(email);
        return userRepository.save(oldUser);
    }

    @Override
    public User updateUsername(User user, String username) {
        if (user == null || username == null) {
            return user;
        }
        User oldUser = userRepository.findByUsername(username);
        AssertUtils.isNull(oldUser, IllegalStateException.class, "该用户已存在");
        oldUser.setUsername(username);
        return userRepository.save(oldUser);
    }
}
