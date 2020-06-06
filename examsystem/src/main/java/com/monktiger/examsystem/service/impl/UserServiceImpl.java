package com.monktiger.examsystem.service.impl;

import com.monktiger.examsystem.entity.Page;
import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.mapper.UserMapper;
import com.monktiger.examsystem.service.UserService;
import com.monktiger.examsystem.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Object> updateUser(String openId, HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>();
        String name = HttpServletRequestUtil.getString(request,"name");
        String nickname = HttpServletRequestUtil.getString(request,"nickname");
        User user = new User();
        user.setName(name);
        user.setNickname(nickname);
        user.setOpenId(openId);
        userMapper.updateByPrimaryKey(user);
        modelMap.put("status",1);
        modelMap.put("msg","更新成功");
        return modelMap;
    }

    @Override
    public Map<String, Object> banUser(String openId) {
        Map<String,Object> modelMap = new HashMap<>();
        User user = new User();
        user.setOpenId(openId);
        user.setAvailable(false);
        userMapper.updateByPrimaryKey(user);
        modelMap.put("status",1);
        modelMap.put("msg","封禁成功");
        return modelMap;
    }

    @Override
    public Map<String, Object> getUser(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String search = HttpServletRequestUtil.getString(request, "search");
        int pageNum = HttpServletRequestUtil.getInt(request, "pageNum");
        if (pageNum == 0) pageNum = 1;
        int total = userMapper.getUserCount(search);
        Page page = new Page(pageNum,total);
        List<User> userList= userMapper.getCountUser(page.getPageSize(),page.getStartIndex(),search);
        modelMap.put("pageNum", pageNum);
        modelMap.put("pages", page.getTotalPage());
        modelMap.put("total", page.getTotalRecord());
        modelMap.put("userList",userList);
        modelMap.put("status",1);
        modelMap.put("msg","返回成功");
        return modelMap;
    }
}
