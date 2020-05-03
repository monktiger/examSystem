package com.monktiger.examsystem.service.impl;

import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.service.ExamService;
import org.springframework.stereotype.Service;

@Service
public class ExamServiceImpl implements ExamService {
    @Override
    public User selectByPrimaryKey(String openId) {
        return null;
    }

    @Override
    public int deleteByPrimaryKey(String openId) {
        return 0;
    }

    @Override
    public int insert(User user) {
        return 0;
    }

    @Override
    public int insertSelective(User user) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(User user) {
        return 0;
    }
}
