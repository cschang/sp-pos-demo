package com.philip.lab.pos.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.philip.lab.pos.common.JwtUtils;
import com.philip.lab.pos.dto.Result;
import com.philip.lab.pos.mapper.UserMapper;
import com.philip.lab.pos.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Result<String> register(User user) {
        // 1. 檢查帳號是否已存在
        Long count = baseMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername()));
        if (count > 0) {
            return Result.error(400, "帳號已存在");
        }

        // 2. 檢查手機號碼是否已存在 (對應 Django unique=True)
        if (user.getPhone() != null) {
            Long phoneCount = baseMapper.selectCount(new LambdaQueryWrapper<User>()
                    .eq(User::getPhone, user.getPhone()));
            if (phoneCount > 0) {
                return Result.error(400, "手機號碼已被註冊");
            }
        }

        // 3. 密碼加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 4. 設定預設值 (對應 Django 預設行為)
        user.setActive(true);
        user.setStaff(false);
        if (user.getRole() == null) {
            user.setRole("CUSTOMER");
        }

        // 5. 儲存
        this.save(user);
        return Result.success("註冊成功");
    }

    @Override
    public User getUserDetail(Long id) {
        return baseMapper.selectUserWithStore(id);
    }

    public String login(String username, String rawPassword) {
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));

        if (user != null) {
            // 使用 matches 方法比對：(明文, 資料庫加密後的密碼)
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return jwtUtils.generateToken(username);
            }
        }
        return null;
    }

}