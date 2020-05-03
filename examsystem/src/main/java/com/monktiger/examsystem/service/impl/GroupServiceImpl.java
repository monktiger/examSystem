package com.monktiger.examsystem.service.impl;

import com.monktiger.examsystem.entity.Group;
import com.monktiger.examsystem.mapper.GroupMapper;
import com.monktiger.examsystem.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupMapper groupMapper;

    /**
     *  建立小组
     * @param group
     * @return 小组 id
     * @throws Exception
     */
    @Override
    public int createGroup(Group group) throws Exception {
        group.setStatus(1);

        /**
         * 创建唯一 字符串作为 groupId
         */
        group.setGroupId(UUID.randomUUID().toString());
        return groupMapper.insert(group);
    }

    /**
     *  加入小组
     * @param group
     * @return
     * @throws Exception
     */
    @Override
    public int joinGroup(Group group) throws Exception {
       List<Group> msg = groupMapper.selectByKeyState(group.getGroupId(),1);
       if(msg!=null){
           group.setStatus(0);
           return groupMapper.insert(group);
       }else{
           return 2;
       }
    }

    /**
     * 允许加入
     * @param openIds groupId
     * @return
     * @throws Exception
     */
    @Override
    public void permitGroup(List<String> openIds,String groupId) throws Exception {
        for(String openId : openIds){
            groupMapper.updatePermission(openId,groupId);
        }
    }

    /**
     *  搜索小组byId
     * @param groupId
     * @return
     * @throws Exception
     */
    @Override
    public Group selectGroupById(String openId,String groupId) throws Exception {
        return groupMapper.selectByPrimaryKey(openId,groupId);
    }

    @Override
    public List<Group> selectSelfGroup(String openId) throws Exception {
        return groupMapper.selectSelfGroup(openId);
    }

    /**
     * 小组名模糊搜索   %名字%
     * @param name
     * @return
     * @throws Exception
     */
    @Override
    public List<Group> fuzzyGroupByName(String name) throws Exception {
        return groupMapper.fuzzySelectByGroupName(name);
    }

    /**
     *  删除相应组员
     * @param groupId openId
     * @param openId
     * @return
     * @throws Exception
     */
    @Override
    public int deleteGroup(String groupId, String openId) throws Exception {
        return groupMapper.deleteByPrimaryKey(openId,groupId);
    }

    /**
     *   解散小组
     * @param groupId
     * @return
     * @throws Exception
     */
    @Override
    public int dissolve(String groupId) throws Exception {
        return groupMapper.dissolveByGroupId(groupId);
    }

    /**
     * 列出小组 内所有成员
     * @param groupId
     * @return
     * @throws Exception
     */
    @Override
    public List<Group> listGroup(String groupId) throws Exception {
        List<Group> groups;
        groups = groupMapper.selectByKeyState(groupId,0);
        List<Group> group = groupMapper.selectByKeyState(groupId,1);
        if(group != null){
            groups.add((Group) group);
            return groups;
        }else{
            System.out.println("不合理数据，无组长小组");
            return groups;
        }
    }

    /**
     * 建立新的小组
     * @param group
     * @return
     * @throws Exception
     */
    @Override
    public int insertGroup(Group group) throws Exception {
        return groupMapper.insert(group);
    }



    @Override
    public int deleteGroup(Group group) throws Exception {
        return groupMapper.deleteByPrimaryKey(group.getOpenId(),group.getGroupId());
    }

    @Override
    public int updateGroup(Group group) throws Exception {
        return groupMapper.updateByPrimaryKey(group);
    }

    @Override
    public Group selectGroupByKey(Group group) throws Exception {
        return groupMapper.selectByPrimaryKey(group.getOpenId(),group.getGroupId());
    }
}
