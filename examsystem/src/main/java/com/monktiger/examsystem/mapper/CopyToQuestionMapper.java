package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Copy;
import com.monktiger.examsystem.entity.CopyToQuestion;
import com.monktiger.examsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
}