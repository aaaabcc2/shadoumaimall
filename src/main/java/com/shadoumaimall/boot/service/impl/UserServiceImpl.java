package com.shadoumaimall.boot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shadoumaimall.boot.common.Constants;
import com.shadoumaimall.boot.controller.domain.UserRequest;
import com.shadoumaimall.boot.entity.User;
import com.shadoumaimall.boot.exception.ServiceException;
import com.shadoumaimall.boot.mapper.UserMapper;
import com.shadoumaimall.boot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shadoumaimall.boot.utils.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 啊啊啊啊不吵吵
 * @since 2023-01-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    // key是code value是当前的时间戳
    private static final Map<String, Long> CODE_MAP = new ConcurrentHashMap<>();
    private static final long TIME_IN_MS5 = 5 * 60 * 1000;
    @Autowired
    EmailUtils emailUtils;

    @Override
    public User login(User user) {
        User dbUser = null;
        try {
            dbUser = getOne(new UpdateWrapper<User>().eq("account", user.getAccount()));
        } catch (Exception e) {
            throw new RuntimeException("系统错误", e);
        }
        if(dbUser == null){
            throw new ServiceException("未找到用户");
        }
        if(! dbUser.getPassword().equals(user.getPassword())){
            throw new ServiceException("用户名或密码错误");
        }
        return dbUser;
    }

    @Override
    public String passwordReset(UserRequest userRequest) {
        String email = userRequest.getEmail();
        User dbUser = getOne(new UpdateWrapper<User>().eq("email", email));
        if(dbUser == null) {
            throw new ServiceException("未找到用户");
        }
        validateEmail(userRequest.getEmail(), userRequest.getEmailCode());
        String newPass = "123";
        dbUser.setPassword(newPass);
        try {
            updateById(dbUser);
        } catch (Exception e) {
            throw new ServiceException("注册失败", e);
        }
        return newPass;
    }

    @Override
    public void sendEmail(String email, String type) {
        String code = RandomUtil.randomString(6);
        String context = "<b>尊敬的用户：</b><br><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，" +
                "Partner交友网提醒您本次的验证码是：<b>{}</b>，" +
                "有效期5分钟。<br><br><br><b>Partner交友网</b>";
        User user = getOne(new QueryWrapper<User>().eq(("email"), email));
        String html = StrUtil.format(context, code);
        if("REGISTER".equals(type)) {//注册
            //校验邮箱是否已注册

            if(user != null) {
                throw new ServiceException("邮箱已注册");
            }
        }
        ThreadUtil.execAsync(() -> {    //多线程执行异步请求
            emailUtils.sendHtml("【Partner交友网】验证提醒", html, email);
        });
        CODE_MAP.put(email + code, System.currentTimeMillis());
    }

    @Override
    public User register(UserRequest user) {
        validateEmail(user.getEmail(), user.getEmailCode());
        try {
            User saveUser = new User();
            BeanUtils.copyProperties(user, saveUser);
            //存储用户
            return saveUser(saveUser);
        } catch (ServiceException e) {
            throw new RuntimeException("系统异常", e);
        }
    }


    /***
     * 校验邮箱
     * @param emailCode
     */
    private void validateEmail(String email, String emailCode) {
        //校验邮箱
        String key = email + emailCode;
        Long timestamp = CODE_MAP.get(key);
        if(timestamp == null) {
            throw new ServiceException("请先验证邮箱");
        }
        if(timestamp + TIME_IN_MS5 < System.currentTimeMillis()) {     //过期
            throw new ServiceException("验证码过期");
        }
        CODE_MAP.remove(key);
    }

    private User saveUser(User user) {
        User dbUser = getOne(new UpdateWrapper<User>().eq("account", user.getAccount()));
        if(dbUser != null){
            throw new ServiceException("用户已注册");
        }
        if(StrUtil.isBlank(user.getName())){
//            String name = Constants.USER_NAME_PREFIX + DateUtil.format(new Date(), Constants.DATE_RULE_YYYYMMDD + RandomUtil.randomString(4));
//            user.setName(name);
            user.setName("系统用户" + RandomUtil.randomString(6));
        }
        if(StrUtil.isBlank(user.getPassword())){
            user.setPassword("123");
        }
        //设置唯一标识
        user.setUid(IdUtil.fastSimpleUUID());
        try {
            save(user);
        } catch (Exception e) {
            throw new RuntimeException("注册失败", e);
        }
        return user;
    }
}
