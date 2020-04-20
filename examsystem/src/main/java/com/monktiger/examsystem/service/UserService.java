package com.monktiger.examsystem.service;

import com.monktiger.examsystem.entity.User;

/**
 *  获取个人信息
 *
 *  管理 Group
 *
 *  管理 exam
 */
public interface UserService {
    public int insertUser(User user)throws Exception;

    public int deleteUser(String openId)throws Exception;

    public int updateUser(User user)throws Exception;

    public User selectUserByKey(String openId)throws Exception;
}
