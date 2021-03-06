package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TbGroupDAO继承基类
 */
@Mapper
@Repository
public interface GroupMapper  {
    Group selectByPrimaryKey(String openId, String groupId);

//    /**
//     * 判断用户是否在组内
//     * @param openId
//     * @param groupId
//     * @return
//     */
    // Integer checkGroup(String openId, String groupId);

    List<Group> selectByKeyState(String groupId, int status);

//    List<String> selectIdsByKeyState(String groupId,int status);

    List<Group> fuzzySelectByGroupName(String name);

    List<Group> selectSelfGroup(String openId,Integer status);

    int deleteByPrimaryKey(String openId, String groupId);

    int dissolveByGroupId(String groupId);

    int insert(Group group);

    int insertSelective(Group group);

    int updateByPrimaryKey(Group group);

    int updatePermission(@Param("openId") String openId, @Param("groupId") String groupId);

    int getGroupCount(@Param("search") String search);

    List<Group> getGroup(int startIndex, int pageSize, String search);

    int updateGroupList(@Param("groupId") String groupId, @Param("name") String name);

}