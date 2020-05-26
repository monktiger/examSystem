package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Copy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CopyMapper {
    int insert(Copy record);

    int insertSelective(Copy record);

    int deleteByPrimaryKey(Integer copyId);

    int updateJudge(Integer copyId,String judge );

    /**
     * 通过openId和examId获得copy
     * @param openId
     * @param examId
     * @return
     */
    Copy selectByAssociaiton(@Param("openId") String openId, @Param("examId") int examId);
    int updateByPrimaryKeySelective(Copy record);

    int updateByPrimaryKey(Copy record);

    Copy selectByPrimaryKey(Integer copyId);

    Copy selectByPrimaryKey(String openId,Integer examId);

    List<Copy> selectByGroupAndExam(Integer examId, String groupId);

    int checkCopyPublisher(Integer copyId,String openId);
}