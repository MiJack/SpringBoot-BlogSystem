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
        if (authentication == null || !authentication.isAuthenticated()) {
            return Response.ok(null).code(0).msg("接口未授权");
        }
        RestfulApiToken token = (RestfulApiToken) authentication;
        return Response.ok(token.getUser())
                .msg("登录成功");
    }
}
