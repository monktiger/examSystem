package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Admin;
import com.monktiger.examsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AdminMapper {
    int deleteByPrimaryKey(Admin key);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin checkUser(@Param("username") String username, @Param("password") String password);
}