package com.monktiger.examsystem.controller.home;

import com.monktiger.examsystem.cache.JedisUtil;
import com.monktiger.examsystem.entity.Group;
import com.monktiger.examsystem.service.impl.GroupServiceImpl;
import com.monktiger.examsystem.utils.resultHander.CommonErrorEnum;
import com.monktiger.examsystem.utils.resultHander.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/group")
@RestController
public class GroupController {

    @Autowired
    private GroupServiceImpl groupService;
    @Autowired
    private JedisUtil jedisUtil;


    /**
     * 创建组
     * @param groupName
     * @return
     * @throws Exception
     */
    @RequestMapping("/create")
    public Object create(String groupName) throws Exception {
        String openId = jedisUtil.STRINGS.get("openId");
        Group group = new Group(groupName);
        group.setOpenId(openId);
        //groupId为6位字符组合所以我通过md5+时间戳生成6位随机值作为groupId

        if(groupService.insertGroup(group)==1){
            return new ResponseBean<>(true,"创建 group 成功", CommonErrorEnum.SUCCESS_OPTION);
        }else{
            return new ResponseBean<>(false,"创建 group 失败",CommonErrorEnum.FAILED_QUESTION);
        }
    }

    /**
     * 加入组
     * @param groupId
     * @return
     * @throws Exception
     */
    @RequestMapping("/join")
    public Object join(String groupId)throws Exception{
        String openId = jedisUtil.STRINGS.get("openId");
        Group group = new Group(groupId);
        group.setOpenId(openId);
        int state = groupService.joinGroup(group);
        if(state==1){
            return new ResponseBean<>(true,"加入成功",CommonErrorEnum.SUCCESS_OPTION);
        }else if(state==0){
            return new ResponseBean<>(false,"",CommonErrorEnum.FAILED_QUESTION);
        }else{
            return new ResponseBean<>(false,"", CommonErrorEnum.FAILED_QUESTION);
        }
    }

    /**
     * 退出组
     * @param groupId
     * @return
     * @throws Exception
     */
    @RequestMapping("/leave")
    public Object leave(String groupId)throws Exception{
        String openId = jedisUtil.STRINGS.get("openId");
        Group group = new Group(groupId,openId);
        int state = groupService.deleteGroup(group);
        if(state == 1){
            return new ResponseBean<>(true,"退组成功",CommonErrorEnum.SUCCESS_OPTION);
        }else{
            return new ResponseBean<>(false,"退组失败", CommonErrorEnum.FAILED_QUESTION);
        }
    }

    /**
     * 列出 自己相关组
     * @return
     * @throws Exception
     */
    @RequestMapping("/getList")
    public Object getList()throws Exception{
        String openId = jedisUtil.STRINGS.get("openId");
        List<Group> groups = groupService.selectSelfGroup(openId);
        if(groups!=null){
            return new ResponseBean<>(true,groups, CommonErrorEnum.SUCCESS_OPTION);
        }else{
            return new ResponseBean<>(false,"空的",CommonErrorEnum.FAILED_QUESTION);
        }
    }


    /******************************************************  group 管理 相关接口  ****************************************************************/

    /**
     * 删除自己管理组 组员
     * @param groupId
     * @param openIds
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteMember")
    public Object deleteMember(String groupId,List<String> openIds)throws Exception{
        String openId = jedisUtil.STRINGS.get("openId");
        Group aim_group = groupService.selectGroupById(groupId,openId);
        if(aim_group==null){
            return new ResponseBean<>(false,"查无此人",CommonErrorEnum.FAILED_QUESTION);
        }else if(aim_group.getStatus()==0){
            return new ResponseBean<>(false,"非组长无权限",CommonErrorEnum.FAILED_QUESTION);
        }else if(aim_group.getStatus()==1){
            return new ResponseBean<>(true,CommonErrorEnum.SUCCESS_OPTION);
        }else {
            return new ResponseBean<>(false,"删除失败",CommonErrorEnum.FAILED_QUESTION);
        }
    }

    /**
     * 查看小组 成员
     * @param groupId
     * @return
     * @throws Exception
     */
    @RequestMapping("/showMember")
    public Object showMember(String groupId)throws Exception{
        Group group = new Group();
        group.setGroupId(groupId);
        group.setStatus(1);
        List<Group> groups = groupService.selectSelfGroup(groupId);
        return new ResponseBean<>(true,groups,CommonErrorEnum.SUCCESS_OPTION);
    }

    /**
     *  组长解散群 组员退出群
     * @param groupId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public Object delete(String groupId)throws Exception{
        String openId = jedisUtil.STRINGS.get("openId");
        Group group = groupService.selectGroupById(openId,groupId);

        /**
         * 判断 执行操作的是组长 还是 组员
         */
        if(group.getStatus() == 0){
            groupService.deleteGroup(new Group(groupId,openId));
            return new ResponseBean(true,"推出小组成功");
        }else{
            groupService.dissolve(groupId);
            return new ResponseBean(true,"解散小组成功");
        }
    }



}
