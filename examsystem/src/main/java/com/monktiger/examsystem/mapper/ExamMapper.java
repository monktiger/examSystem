package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TbExamDAO继承基类
 */
@Mapper
@Repository
public interface ExamMapper  {
    User selectByPrimaryKey(String openId);

    int deleteByPrimaryKey(String openId);

    int insert(User user);

    int insertSelective(User user);

    int updateByPrimaryKey(User user);
}