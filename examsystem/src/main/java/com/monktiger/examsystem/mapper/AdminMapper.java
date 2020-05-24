package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AdminMapper {
    int deleteByPrimaryKey(Admin key);

    int insert(Admin record);

    int insertSelective(Admin record);
}