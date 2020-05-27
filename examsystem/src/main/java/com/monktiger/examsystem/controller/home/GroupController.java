package com.monktiger.examsystem.controller.home;

import com.monktiger.examsystem.cache.JedisUtil;
import com.monktiger.examsystem.entity.Group;
import com.monktiger.examsystem.mapper.GroupMapper;
import com.monktiger.examsystem.service.impl.GroupServiceImpl;
import com.monktiger.examsystem.util.MD5Util;
import com.monktiger.examsystem.utils.resultHander.CommonErrorEnum;
import com.monktiger.examsystem.utils.resultHander.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/group")
@RestController
@CrossOrigin
public class GroupController {

    @Autowired
    private GroupServiceImpl groupService;

    @Autowired
    private GroupMapper groupMapper;

    private JedisUtil jedisUtil;

    @Autowired
    private JedisUtil.Keys jedisUtilKeys;

    @Autowired
    private JedisUtil.Strings jedistUtilStrings;

    private Map<String,Object> modelMap = new HashMap<>();



    /**
     * 创建组
     * @param name
     * @return
     * @throws Exception
     */
    @RequestMapping("/create")
    public Map<String,Object> create(String name, HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        if(token!=null&&jedisUtilKeys.exists("token")){

            String openId = jedistUtilStrings.get("token");
//            String openId = "openId";
            Group group = new Group(name);
            group.setOpenId(openId);
            //groupId为6位字符组合所以我通过md5+时间戳生成6位随机值作为groupId

            /**
             * 创建唯一 字符串作为 groupId
             */
            String groupId = MD5Util.getMd5(String.valueOf(System.currentTimeMillis()),6);;
            group.setGroupId(groupId);
            if(groupService.insertGroup(group)==1){
                modelMap.put("groupId",groupId);
                modelMap.put("status",1);
                modelMap.put("msg","创建成功");
            }else{
                modelMap.put("groupId",groupId);
                modelMap.put("status",0);
                modelMap.put("msg","创建失败");
            }

        }else{
            modelMap.put("state",0);
            modelMap.put("msg","请登录后操作");
        }
        return modelMap;
    }


    /**
     * 加入组
     * @param groupId
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/join",method = RequestMethod.GET)
    public Map<String,Object> join(String groupId, HttpServletRequest request)throws Exception{
        String token = request.getHeader("token");

        if(token!=null&&jedisUtilKeys.exists("token"))
        {
            String openId = token;
            Group group = new Group(groupId);
            group.setGroupId(groupId);
            group.setOpenId(openId);
            int state = groupService.joinGroup(group);
            if(state==1){
                modelMap.put("status",1);
                modelMap.put("msg","成功加入");
//            return new ResponseBean<>(true,"加入成功",CommonErrorEnum.SUCCESS_OPTION);
            }else {
                modelMap.put("status", 0);
                modelMap.put("msg", "失败加入");
//            return new ResponseBean<>(false,"",CommonErrorEnum.FAILED_QUESTION);
            }
        }else{
            modelMap.put("state",0);
            modelMap.put("msg","请登录后操作");
        }

        return modelMap;
    }

    /**
     * 退出组
     * @param groupId
     * @return
     * @throws Exception
     */
    @RequestMapping("/leave")
    public Map<String,Object> leave(String groupId,HttpServletRequest request)throws Exception{
        String token = request.getHeader("token");
        if(token!=null&&jedisUtilKeys.exists("token"))
        {
            String openId = token;
            Group group = new Group(groupId,"openId");
            int state = groupService.deleteGroup(group);
            if(state == 1){
                modelMap.put("status",1);
                modelMap.put("msg","退出成功");

            }else{
//            return new ResponseBean<>(false,"退组失败", CommonErrorEnum.FAILED_QUESTION);
                modelMap.put("status",0);
                modelMap.put("msg","退出失败");
            }
        }else{
            modelMap.put("state",0);
            modelMap.put("msg","请登录后操作");
        }
        return modelMap;
    }

    /**
     * 列出 自己相关组 &
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getList",method= RequestMethod.GET)
    public Object getList(HttpServletRequest request,Integer type)throws Exception{
        String token = request.getHeader("token");
        if(token!=null&&jedisUtilKeys.exists("token"))
        {
            String openId = token;
            List<Group> groups = groupMapper.selectSelfGroup(openId,type);
            modelMap.put("groups",groups);
            modelMap.put("state",1);
            modelMap.put("msg","加入成功");
        }else{
            modelMap.put("state",0);
            modelMap.put("msg","请登录后操作");
        }
//        String openId = jedisUtil.STRINGS.get("openId");
        return modelMap;
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
    @RequestMapping(value="/showMember",method = RequestMethod.GET)
    public Map<String,Object> showMember(String groupId,HttpServletRequest request)throws Exception{
        String token = request.getHeader("token");
        if(token!=null&&jedisUtilKeys.exists("token"))
        {

            modelMap.put("state",1);
            modelMap.put("msg","加入成功");
        }else{
            modelMap.put("state",0);
            modelMap.put("msg","请登录后操作");
        }
        Group group = new Group();
        group.setGroupId(groupId);
        group.setStatus(1);
        List<Group> groups = groupMapper.selectByKeyState(groupId,0);
        modelMap.put("groups",groups);
        modelMap.put("state",1);
        return modelMap;
    }

    /**
     *  组长解散群 组员退出群
     * @param groupId
     * @return
     * @throws Exception
     */
    @RequestMapping("/quit")
    public Map<String,Object> delete(String groupId,HttpServletRequest request)throws Exception{
        String token = request.getHeader("token");
        if(token!=null&&jedisUtilKeys.exists("token"))
        {
            //String openId = jedisUtil.STRINGS.get("openId");
            String openId = token;
            Group group = groupService.selectGroupById(openId,groupId);

            /**
             * 判断 执行操作的是组长 还是 组员
             */
            if(group.getStatus() == 1){
                groupService.deleteGroup(new Group(groupId,openId));
                modelMap.put("state",1);
                modelMap.put("msg","退出小组成功");
            }else{
                groupService.dissolve(groupId);
                modelMap.put("state",2);
                modelMap.put("msg","解散小组成功");
            }
        }else{
            modelMap.put("state",0);
            modelMap.put("msg","请登录后操作");
        }

        return modelMap;
    }



}
