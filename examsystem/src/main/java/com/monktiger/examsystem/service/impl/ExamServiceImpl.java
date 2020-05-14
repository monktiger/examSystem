package com.monktiger.examsystem.service.impl;

import com.monktiger.examsystem.entity.Exam;
import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.service.ExamService;
import org.springframework.stereotype.Service;

@Service
public class ExamServiceImpl implements ExamService {
    @Override
    public Exam selectByPrimaryKey(String openId) {
        return null;
    }

    @Override
    public int deleteByPrimaryKey(String openId) {
        return 0;
    }

    @Override
    public int insert(Exam exam) {
        return 0;
    }

    @Override
    public int insertSelective(Exam exam) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Exam exam) {
        return 0;
    }

    @Override
    public int insertAnswer(Exam exam) {
        return 0;
    }

}
