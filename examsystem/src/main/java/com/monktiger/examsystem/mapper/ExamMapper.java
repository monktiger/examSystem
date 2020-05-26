package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface   ExamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Exam record);

    int insertSelective(Exam record);

    Exam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Exam record);

    List<Exam> getPrepareExam();
    List<Exam> getEndExam();
    List<Exam> getbeginExam();
    /**
     * 获得某组下的全部试卷
     * @param examIdList
     * @return
     */
    List<Exam> selectExamByGroup(@Param("examIdList") List<Integer> examIdList);

    /**
     *仅获得其他情况下的试卷
     * @param examIdList
     * @return
     */
    List<Exam> selectExamByGroupAndStatus(@Param("examIdList") List<Integer> examIdList);
    int updateByPrimaryKey(Exam record);

    int updateexamListStatus(@Param("examList") List<Exam> examList, @Param("status") Integer status);

}