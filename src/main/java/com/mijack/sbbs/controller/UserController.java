package com.mijack.sbbs.controller;

import com.mijack.sbbs.controller.base.BaseController;
import com.mijack.sbbs.exceptions.UserNotFoundException;
import com.mijack.sbbs.model.BlogStatistics;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.service.BlogStatisticsService;
import com.mijack.sbbs.service.UserService;
import com.mijack.sbbs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mr.Yuan
 * @since 2017/10/10
 */
@Controller
public class UserController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    TokenBasedRememberMeServices tokenBasedRememberMeServices;
    @Autowired
    BlogStatisticsService blogStatisticsService;

    @GetMapping("/user.html")
    public String user(Authentication authentication, Model model) {
        if (!Utils.isAuthenticated(authentication)) {
            return "redirect:/login.html";
        }
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("modifyShow", true);
        // 添加blog统计情况
        BlogStatistics blogStatistics = blogStatisticsService.blogStatistics(user);
        model.addAttribute("blogStatistics", blogStatistics);
        return "user/profile";
    }

    @GetMapping("/user/{id}")
    public String user(@PathVariable("id") long id, Model model) {
        User user = userService.findUser(id);
        if (user == null) {
            throw new UserNotFoundException("id为" + id + "的用户不存在");
        }
        model.addAttribute("user", user);
        model.addAttribute("modifyShow", false);
        // 添加blog统计情况
        BlogStatistics blogStatistics = blogStatisticsService.blogStatistics(user);
        model.addAttribute("blogStatistics", blogStatistics);
        return "user/profile";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           @RequestParam("name") String name,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("remember-me") String rememberMe) {
        User user = userService.createUser(name, email, password);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if ("on".equals(rememberMe)) {
            tokenBasedRememberMeServices.onLoginSuccess(httpServletRequest, httpServletResponse, authentication);
        }
        return "redirect:/index.html";
    }
}
