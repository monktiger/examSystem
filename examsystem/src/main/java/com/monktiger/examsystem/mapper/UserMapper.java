package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Group;
import com.monktiger.examsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TbUserDAO继承基类
 */
@Mapper
@Repository
public interface UserMapper {
    User selectByPrimaryKey(String openId);

    List<User> selectByIds(@Param("groups")List<Group> groups);

    int deleteByPrimaryKey(String openId);

    int insert(User user);

    int insertSelective(User user);

    int updateByPrimaryKey(User user);
}