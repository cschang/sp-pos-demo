package com.philip.lab.pos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.philip.lab.pos.dto.Result;
import com.philip.lab.pos.model.User;

public interface UserService extends IService<User> {
    public String login(String username, String password);
    Result<String> register(User user);
    User getUserDetail(Long id);
}

