package com.mijack.sbbs.service;

import com.mijack.sbbs.model.User;

public interface UserService {

    User login(String username, String email, String password);
}
