package com.monktiger.examsystem.service;

import com.monktiger.examsystem.entity.User;

public interface ExamService {
    User selectByPrimaryKey(String openId);

    int deleteByPrimaryKey(String openId);

    int insert(User user);

    int insertSelective(User user);

    int updateByPrimaryKey(User user);
}
