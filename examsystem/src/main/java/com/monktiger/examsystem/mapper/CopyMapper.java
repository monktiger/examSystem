package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Copy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CopyMapper {
    int deleteByPrimaryKey(Integer copyId);

    int insert(Copy record);

    int insertSelective(Copy record);

    Copy selectByPrimaryKey(Integer copyId);

    int updateByPrimaryKeySelective(Copy record);

    int updateByPrimaryKey(Copy record);
}