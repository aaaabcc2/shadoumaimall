package com.shadoumaimall.boot.controller;

import com.shadoumaimall.boot.common.Result;
import com.shadoumaimall.boot.controller.domain.UserRequest;
import com.shadoumaimall.boot.entity.User;
import com.shadoumaimall.boot.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "无权限接口列表")
@RestController
public class WebController {

    @Resource
    IUserService userService;

    @GetMapping("/")
    @ApiOperation(value = "版本校验接口")
    public String version(){
        String ver = "partner-back-0.0.1-SNAPSHOT";
        Package aPackage = WebController.class.getPackage();
        String title = aPackage.getImplementationTitle();
        String version = aPackage.getImplementationVersion();
        if(title != null && version != null){
            ver = String.join("-", title, version);
        }
        return ver;
    }
    @ApiOperation(value = "用户登录接口")
    @RequestMapping("/login")
    public Result login(@RequestBody User user){
        User res = userService.login(user);
        return Result.success(res);
    }

    @ApiOperation(value = "用户注册接口")
    @PostMapping("/register")
    public Result register(@RequestBody UserRequest user){
        User res = userService.register(user);
        return Result.success(res);
    }

    @ApiOperation(value = "邮箱验证接口")
    @RequestMapping("/email")
    public Result sendEmail(@RequestParam String email,  @RequestParam String type){
        userService.sendEmail(email, type);
        return Result.success();
    }

    @ApiOperation(value = "密码重置接口")
    @PostMapping("/password/reset")
    public Result passwordReset(@RequestBody UserRequest userRequest){
        String newPass = userService.passwordReset(userRequest);
        return Result.success(newPass);
    }
}
