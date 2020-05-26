package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ExamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Exam record);

    int insertSelective(Exam record);

    Exam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Exam record);

    /**
     * 获得某组下的全部试卷
     * @param examIdList
     * @return
     */
    List<Exam> selectExamByGroup(List<Integer> examIdList);

    /**
     *仅获得其他情况下的试卷
     * @param examIdList
     * @return
     */
    List<Exam> selectExamByGroupAndStatus(List<Integer> examIdList);
    int updateByPrimaryKey(Exam record);
}