package com.monktiger.examsystem.service.impl;

import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.mapper.UserMapper;
import com.monktiger.examsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    /**
     *  注册 登录
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public User login(User user) throws Exception {
        User msg = userMapper.selectByPrimaryKey(user.getOpenId());
        return msg;
    }

    @Override
    public int register(User user)throws Exception{
        return userMapper.insert(user);
    }

    @Override
    public int insertUser(User user) throws Exception {
        userMapper.insert(user);
        return 0;
    }

    @Override
    public int deleteUser(String openId) throws Exception {
        userMapper.deleteByPrimaryKey(openId);
        return 0;
    }

    @Override
    public int updateUser(User user) throws Exception {
        userMapper.updateByPrimaryKey(user);
        return 0;
    }

    @Override
    public User selectUserByKey(String openId) throws Exception {

        return userMapper.selectByPrimaryKey(openId);
    }
}
