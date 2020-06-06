package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Copy;
import com.monktiger.examsystem.entity.Exam;
import com.monktiger.examsystem.entity.Group;
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

    Copy selectByPrimaryKey(@Param("copyId") Integer copyId);

    //Copy selectByPrimaryKey(String openId,Integer examId);

    List<Copy> selectByGroupAndExam(@Param("examId") Integer examId, @Param("groupId") String groupId);

    int checkCopyPublisher(@Param("copyId") Integer copyId, @Param("openId") String openId);

    int createCopyList(
            @Param("groupId") String groupId, @Param("groupList") List<Group> groupList, @Param("id") Integer id, @Param("status") Integer status);

    int updateByExamList(@Param("examList") List<Exam> examList);

    void updateByExam(@Param("id") Integer id, @Param("status") Integer status);

    int createCopyList2(@Param("groupList") List<Group> groupList, @Param("id") Integer id, @Param("status") int status);

    int deleteByexam(@Param("examId") int examId);

    int deleteByGroup(@Param("groupId") String groupId);
}