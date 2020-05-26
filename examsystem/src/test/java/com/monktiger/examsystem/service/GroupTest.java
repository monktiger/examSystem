package com.monktiger.examsystem.service;

import com.monktiger.examsystem.entity.Group;
import com.monktiger.examsystem.mapper.GroupMapper;
import com.monktiger.examsystem.service.impl.GroupServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GroupTest {
    @Autowired
    GroupServiceImpl groupService;
    @Autowired
    GroupMapper groupMapper;

    @Test
    public void select() throws Exception{
//        List<Group> groups = groupMapper.selectByKeyState("group_id",1);
        List<Group> groups = groupMapper.selectSelfGroup("open_id",1);
        for(Group group : groups){
            System.out.println(group.toString());
        }
    }

    @Test
    public void testCreate() throws Exception {
        Group group = new Group();
        group.setName("欢乐小组");
        group.setGroupId("group_id");
        group.setOpenId("open_id");
        groupService.createGroup(group);
    }

    @Test
    public void testJoin()throws Exception{
        Group group = new Group();
        group.setGroupId("aaaaaa");
        group.setOpenId("open_ids");
        groupService.joinGroup(group);
    }

    @Test
    public void quit()throws  Exception{
        groupService.deleteGroup("group_id","open_id");
    }

    @Test
    public void dissolve()throws Exception{
        groupService.dissolve("group_id");
    }

    @Test
    public void listAll()throws Exception{
//        List<Group> groups = groupService.listGroup("group_Id",0);
        List<Group> groups = groupService.selectSelfGroup("open_id",0);
        for(Group group : groups){
            System.out.println(group.toString());
        }
    }
}
