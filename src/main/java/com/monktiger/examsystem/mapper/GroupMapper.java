package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.User;
import org.springframework.stereotype.Repository;

/**
 * TbGroupDAO继承基类
 */
@Repository
public interface GroupMapper  {
    User selectByPrimaryKey(String openId);

    int deleteByPrimaryKey(String openId);

    int insert(User user);

    int insertSelective(User user);

    int updateByPrimaryKey(User user);
}