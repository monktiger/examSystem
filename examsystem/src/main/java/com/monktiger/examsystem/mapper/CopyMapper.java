package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Copy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CopyMapper {
    int deleteByPrimaryKey(Integer copyId);

    int insert(Copy record);

    int insertSelective(Copy record);

    Copy selectByPrimaryKey(Integer copyId);

    /**
     * 通过openId和examId获得copy
     * @param openId
     * @param examId
     * @return
     */
    Copy selectByAssociaiton(@Param("openId") String openId, @Param("examId") int examId);
    int updateByPrimaryKeySelective(Copy record);

    int updateByPrimaryKey(Copy record);
}