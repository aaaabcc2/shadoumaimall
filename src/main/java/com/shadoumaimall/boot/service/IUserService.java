package com.shadoumaimall.boot.service;

import com.shadoumaimall.boot.controller.domain.UserRequest;
import com.shadoumaimall.boot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 啊啊啊啊不吵吵
 * @since 2023-01-16
 */
public interface IUserService extends IService<User> {

    User login(UserRequest user);

    String passwordReset(UserRequest userRequest);

    void sendEmail(String email, String type);

    User register(UserRequest user);
}
