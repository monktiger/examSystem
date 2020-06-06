package com.monktiger.examsystem.service;

import com.monktiger.examsystem.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *  获取个人信息
 *
 *  管理 Group
 *
 *  管理 exam
 */
public interface UserService {

    /**
     * 登录 （登陆处理）
     * @param user
     * @return
     * @throws Exception
     */
    public User login(User user)throws Exception;

    /**
     * 注册环节
     * @param user
     * @return
     * @throws Exception
     */
    public int register(User user)throws Exception;

    public int insertUser(User user)throws Exception;

    public int deleteUser(String openId)throws Exception;

    public int updateUser(User user)throws Exception;

    public User selectUserByKey(String openId)throws Exception;

    Map<String, Object> updateUser(String openId, HttpServletRequest request);

    Map<String, Object> banUser(String openId);

    Map<String, Object> getUser(HttpServletRequest request);
}
