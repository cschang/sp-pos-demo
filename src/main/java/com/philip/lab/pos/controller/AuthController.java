package com.philip.lab.pos.controller;

import com.philip.lab.pos.dto.Result;
import com.philip.lab.pos.model.User;
import com.philip.lab.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        String token = userService.login(username, password);
        
        if (token != null) {
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return Result.success(response);
        } else {
            return Result.error(401, "帳號或密碼錯誤");
        }
    }
    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return Result.error(400, "帳號或密碼不能為空");
        }
        return userService.register(user);
    }
}