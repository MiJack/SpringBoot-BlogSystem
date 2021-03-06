package com.mijack.sbbs.controller.api;

import com.mijack.sbbs.auth.token.RestfulApiToken;
import com.mijack.sbbs.model.User;
import com.mijack.sbbs.service.AuthService;
import com.mijack.sbbs.service.UserService;
import com.mijack.sbbs.utils.Utils;
import com.mijack.sbbs.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class ApiUserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    @ResponseBody
    public Response login(Authentication authentication) {
        RestfulApiToken token = (RestfulApiToken) authentication;
        String email = token.getEmail();
        if (authentication == null) {
            return Response.ok(null).code(0).msg("账号或者密码错误");
        }
        if (!authentication.isAuthenticated()) {
            if (!Utils.isEmpty(email) && !Utils.isEmail(email)) {
                return Response.ok(null).code(0).msg("邮箱格式错误");
            } else {
                return Response.ok(null).code(0).msg("账号或者密码错误");
            }
        }
        // 登陆用户
        User user = token.getUser();
        String userToken = authService.createToken(user);
        token.setToken(userToken);
        return Response.ok(token.getUser()).put("token", token.getToken())
                .msg("登录成功");
    }

    @GetMapping()
    @ResponseBody
    public Response user(Authentication authentication) {
        if (!Utils.isAuthenticated(authentication)) {
            return Response.ok(null).code(0).msg("接口未授权");
        }
        RestfulApiToken token = (RestfulApiToken) authentication;
        return Response.ok(token.getUser())
                .msg("登录成功");
    }

    @PatchMapping("/password")
    @ResponseBody
    public Response password(
            @RequestParam("old-password") String oldPassword,
            @RequestParam("new-password") String newPassword,
            Authentication authentication) {
        if (!Utils.isAuthenticated(authentication)) {
            return Response.ok(null).code(0).msg("接口未授权");
        }
        RestfulApiToken token = (RestfulApiToken) authentication;
        User user = token.getUser();
        if (!Utils.isEquals(oldPassword, newPassword)) {
            return Response.failed("用户原密码错误");
        }
        user.setPassword(newPassword);
        user = userService.updatePassword(user, oldPassword, newPassword);
        return Response.ok(user)
                .msg("密码修改成功");
    }

    @PutMapping("/profile/email")
    @ResponseBody
    public Response<User> profileEmail(
            @RequestParam("email") String email,
            Authentication authentication) {
        if (!Utils.isAuthenticated(authentication)) {
            return Response.<User>ok(null).code(0).msg("接口未授权");
        }
        if (email == null) {
            return Response.failed("缺少必要的参数");
        }
        RestfulApiToken token = (RestfulApiToken) authentication;
        User user = token.getUser();
        user = userService.updateEmail(user, email);
        // 处理结果
        return Response.ok(user)
                .msg("邮箱修改成功");
    }

    @PutMapping("/profile/username")
    @ResponseBody
    public Response<User> profileUsername(
            @RequestParam("username") String username,
            Authentication authentication) {
        if (!Utils.isAuthenticated(authentication)) {
            return Response.<User>ok(null).code(0).msg("接口未授权");
        }
        if (username == null) {
            return Response.failed("缺少必要的参数");
        }
        RestfulApiToken token = (RestfulApiToken) authentication;
        User user = token.getUser();
        user = userService.updateUsername(user, username);
        // 处理结果
        return Response.ok(user)
                .msg("用户名修改成功");
    }
}
