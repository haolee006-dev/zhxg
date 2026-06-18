package com.student.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.student.system.entity.User;

public interface UserService extends IService<User> {
    User login(String username, String password);
    User getUserByUsername(String username);
}
