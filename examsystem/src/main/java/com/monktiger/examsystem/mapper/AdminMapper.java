package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * AdminDAO继承基类
 */
@Mapper
public interface AdminMapper  {
    User selectByPrimaryKey(String openId);

    int deleteByPrimaryKey(String openId);

    int insert(User user);

    int insertSelective(User user);

    int updateByPrimaryKey(User user);
}