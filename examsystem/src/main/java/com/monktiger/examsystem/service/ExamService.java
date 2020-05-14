package com.monktiger.examsystem.service;

import com.monktiger.examsystem.entity.Exam;
import com.monktiger.examsystem.entity.User;

public interface ExamService {

/***************************************************出题者角度***************************************************************/

    Exam selectByPrimaryKey(String id);

    int deleteByPrimaryKey(String openId);

    int insert(Exam exam);

    int insertSelective(Exam exam);

    int updateByPrimaryKey(Exam exam);


/******************************************************答题者角度************************************************************/

   int insertAnswer(Exam exam);



}
