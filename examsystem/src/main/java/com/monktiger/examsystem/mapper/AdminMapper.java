package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Admin;
import com.monktiger.examsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * AdminDAO继承基类
 */
@Mapper
@Repository
public interface AdminMapper  {
    User selectByPrimaryKey(String openId);

    int deleteByPrimaryKey(String openId);

    int insert(Admin admin);

    int insertSelective(Admin admin);

    int updateByPrimaryKey(Admin admin);
}