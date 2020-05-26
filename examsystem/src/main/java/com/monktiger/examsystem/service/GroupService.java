package com.monktiger.examsystem.service;

import com.monktiger.examsystem.entity.Group;

import java.util.List;

public interface GroupService {

    /**
     *  创建小组
     * @param group
     * @return 返回groupId
     * @throws Exception
     */
    public int createGroup(Group group)throws Exception;

    /**
     *  加入小组(暂时加入)
     * @param group
     * @return 返回所加入的小组
     * @throws Exception
     */
    public int joinGroup(Group group)throws Exception;

    /**
     *  允许加入小组
     * @param openIds groupId
     * @return 1/0
     * @throws Exception
     */
    public void permitGroup(List<String> openIds,String groupId)throws Exception;

    /**
     * 搜索小组 byGroupId
     * @param groupId
     * @return Group(name id 组长)
     * @throws Exception
     */
    public Group selectGroupById(String openId,String groupId)throws Exception;

    /**
     *  列出有关自己的组
     * @param openId
     * @return
     * @throws Exception
     */
    public List<Group> selectSelfGroup(String openId,Integer type)throws Exception;

    /**
     * 模糊搜索小组 byGroupName
     * @param name
     * @return List<Group>
     * @throws Exception
     */
    public List<Group> fuzzyGroupByName(String name)throws Exception;

    /**
     * 删除组员 推出组
     * @param groupId openId
     * @return 1/0
     * @throws Exception
     */
    public int deleteGroup(String groupId,String openId)throws Exception;

    /**
     * 解散组队
     * @param groupId
     * @return 1/0
     * @throws Exception
     */
    public int dissolve(String groupId)throws Exception;

    public List<Group> listGroup(String openId,Integer type)throws Exception;

    public int insertGroup(Group group)throws Exception;

    public int deleteGroup(Group group)throws Exception;

    public int updateGroup(Group group)throws Exception;

    public Group selectGroupByKey(Group group)throws Exception;
}
