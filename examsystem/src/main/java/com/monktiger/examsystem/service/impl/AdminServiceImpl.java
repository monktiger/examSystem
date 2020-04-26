package com.monktiger.examsystem.service.impl;

import com.monktiger.examsystem.mapper.AdminMapper;
import com.monktiger.examsystem.mapper.ExamMapper;
import com.monktiger.examsystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private ExamMapper examMapper;
    @Override
    public int adminLogin(String password, String username) {
        adminMapper.
        return 0;
    }
}
