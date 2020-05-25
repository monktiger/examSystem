package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Copy;
import com.monktiger.examsystem.entity.CopyToQuestion;
import com.monktiger.examsystem.entity.ExamToQuestion;
import com.monktiger.examsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TbCopyToQuetionDAO继承基类
 */
@Mapper
@Repository
public interface CopyToQuestionMapper {
    User selectByPrimaryKey(String openId);

    CopyToQuestion selectByPrimaryKey(Integer copyId,Integer id);

    int deleteByPrimaryKey(String openId);

    int insert(User user);

    int insertSelective(User user);

    int updateByPrimaryKey(User user);

    int checkQuestionType(int copyId,int id);

    int updateByPrimaryKey(CopyToQuestion copyToQuestion);
}