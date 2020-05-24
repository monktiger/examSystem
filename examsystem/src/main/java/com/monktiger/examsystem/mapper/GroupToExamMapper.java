package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.GroupToExamKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GroupToExamMapper {
    int deleteByPrimaryKey(GroupToExamKey key);

    int insert(GroupToExamKey record);

    int insertSelective(GroupToExamKey record);
}