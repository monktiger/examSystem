package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.GroupToExamKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GroupToExamMapper {
    int deleteByPrimaryKey(GroupToExamKey key);

    int insert(GroupToExamKey record);

    int insertSelective(GroupToExamKey record);

    /**
     * 获得examId
     * @param groupId
     * @return
     */
    List<Integer> selectByGroupId(@Param("groupId") String groupId);


    int UpdateAssociation(@Param("id") Integer id,@Param("groupList") List<String> groupList);
}