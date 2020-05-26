package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Copy;
import com.monktiger.examsystem.entity.CopyToQuestion;

import com.monktiger.examsystem.entity.ExamToQuestion;
import com.monktiger.examsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TbCopyToQuetionDAO继承基类
 */
@Mapper
@Repository
public interface CopyToQuestionMapper {
    CopyToQuestion selectByPrimaryKey(@Param("copyId") int copyId, @Param("id") int id);


    int deleteByPrimaryKey(String openId);

    int insert(User user);

    int insertSelective(User user);

    int updateByPrimaryKey(@Param("copyToQuestion") CopyToQuestion copyToQuestion);

    /**
     * 返回type
     * @param copyId
     * @param id
     * @return
     */
    int checkQuestionType(@Param("copyId") int copyId, @Param("id") int id);

    /**
     * 查看是否有题目还没有分数
     * @param copyId
     * @return
     */
    int selectCopyIdScore(@Param("copyId") int copyId);

    List<CopyToQuestion> selectByCopyId(@Param("copyId") Integer copyId);

    int createQuestion(@Param("copyList") List<Copy> copyList, @Param("examToQuestions") List<ExamToQuestion> examToQuestions, @Param("already") boolean already);
}