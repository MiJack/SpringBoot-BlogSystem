package com.mijack.sbbs.controller;

import com.mijack.sbbs.model.User;
import com.mijack.sbbs.service.UserService;
import com.mijack.sbbs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mr.Yuan
 * @since 2017/10/10
 */
@Controller
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    @GetMapping("/user.html")
    public String user(Authentication authentication, Model model) {
        if (!Utils.isAuthenticated(authentication)) {
            return "redirect:/login.html";
        }
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "user/profile";
    }
}
