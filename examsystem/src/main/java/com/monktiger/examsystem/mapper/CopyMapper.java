package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Copy;
import com.monktiger.examsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TbCopyDAO继承基类
 */
@Mapper
@Repository
public interface CopyMapper{
    User selectByPrimaryKey(String openId);

    int deleteByPrimaryKey(String openId);

    int insert(User user);

    int insertSelective(User user);

    int updateByPrimaryKey(User user);
}