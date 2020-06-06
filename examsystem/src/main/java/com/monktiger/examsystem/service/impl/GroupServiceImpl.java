package com.monktiger.examsystem.service.impl;

import com.monktiger.examsystem.entity.CopyToQuestion;
import com.monktiger.examsystem.entity.Group;
import com.monktiger.examsystem.entity.Page;
import com.monktiger.examsystem.mapper.CopyMapper;
import com.monktiger.examsystem.mapper.CopyToQuestionMapper;
import com.monktiger.examsystem.mapper.GroupMapper;
import com.monktiger.examsystem.mapper.GroupToExamMapper;
import com.monktiger.examsystem.service.GroupService;
import com.monktiger.examsystem.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupMapper groupMapper;
    @Autowired
    GroupToExamMapper groupToExamMapper;
    @Autowired
    CopyMapper copyMapper;
    @Autowired
    CopyToQuestionMapper copyToQuestionMapper;
    /**
     * 建立小组
     *
     * @param group
     * @return 小组 id
     * @throws Exception
     */
    @Override
    public int createGroup(Group group) throws Exception {
        group.setStatus(1);
        return groupMapper.insert(group);
    }

    /**
     * 加入小组
     *
     * @param group
     * @return
     * @throws Exception
     */
    @Override
    public int joinGroup(Group group) throws Exception {
        List<Group> msg = groupMapper.selectByKeyState(group.getGroupId(), 0);
        if (!msg.isEmpty()) {
            group.setName(msg.get(0).getName());
            group.setStatus(1);
            return groupMapper.insert(group);
        } else {
            return 2;
        }
    }

    /**
     * 允许加入
     *
     * @param openIds groupId
     * @return
     * @throws Exception
     */
    @Override
    public void permitGroup(List<String> openIds, String groupId) throws Exception {
        for (String openId : openIds) {
            groupMapper.updatePermission(openId, groupId);
        }
    }

    /**
     * 搜索小组byId
     *
     * @param groupId
     * @return
     * @throws Exception
     */
    @Override
    public Group selectGroupById(String openId, String groupId) throws Exception {
        return groupMapper.selectByPrimaryKey(openId, groupId);
    }

    @Override
    public List<Group> selectSelfGroup(String openId, Integer type) throws Exception {
        return groupMapper.selectSelfGroup(openId, type);
    }

    /**
     * 小组名模糊搜索   %名字%
     *
     * @param name
     * @return
     * @throws Exception
     */
    @Override
    public List<Group> fuzzyGroupByName(String name) throws Exception {
        return groupMapper.fuzzySelectByGroupName(name);
    }

    /**
     * 删除相应组员
     *
     * @param groupId openId
     * @param openId
     * @return
     * @throws Exception
     */
    @Override
    public int deleteGroup(String groupId, String openId) throws Exception {
        return groupMapper.deleteByPrimaryKey(openId, groupId);
    }

    /**
     * 解散小组
     *
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
     *
     * @param groupId
     * @return
     * @throws Exception
     */
    @Override
    public List<Group> listGroup(String groupId, Integer type) throws Exception {
        List<Group> groups = groupMapper.selectByKeyState(groupId, type);
        return groups;
    }

    /**
     * 建立新的小组
     *
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
        return groupMapper.deleteByPrimaryKey(group.getOpenId(), group.getGroupId());
    }

    @Override
    public int updateGroup(Group group) throws Exception {
        return groupMapper.updateByPrimaryKey(group);
    }

    @Override
    public Group selectGroupByKey(Group group) throws Exception {
        return groupMapper.selectByPrimaryKey(group.getOpenId(), group.getGroupId());
    }

    @Override
    public Map<String, Object> getGroup(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String search = HttpServletRequestUtil.getString(request, "search");
        int pageNum = HttpServletRequestUtil.getInt(request, "pageNum");
        if (pageNum == 0) pageNum = 1;
        int total = groupMapper.getGroupCount(search);
        Page page = new Page(pageNum, total);
        List<Group> groupList = groupMapper.getGroup(page.getStartIndex(), page.getPageSize(), search);
        modelMap.put("groupList", groupList);
        modelMap.put("pageNum", pageNum);
        modelMap.put("pages", page.getTotalPage());
        modelMap.put("total", page.getTotalRecord());
        modelMap.put("status", 1);
        modelMap.put("msg", "返回成功");
        return modelMap;
    }

    @Override
    public Map<String, Object> updateGroup(String groupId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String name = HttpServletRequestUtil.getString(request, "name");
        groupMapper.updateGroupList(groupId, name);
        modelMap.put("status", 1);
        modelMap.put("msg", "更新成功");
        return modelMap;
    }

    @Override
    public Map<String, Object> deleteGroup(String groupId) {
        Map<String, Object> modelMap = new HashMap<>();
        groupMapper.dissolveByGroupId(groupId);
        groupToExamMapper.deleteByGroup(groupId);
        copyToQuestionMapper.deleteByGroup(groupId);
        copyMapper.deleteByGroup(groupId);
        modelMap.put("status", 1);
        modelMap.put("msg", "更新成功");
        return modelMap;
    }
}