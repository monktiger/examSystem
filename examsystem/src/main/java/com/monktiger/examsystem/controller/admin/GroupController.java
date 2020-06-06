package com.monktiger.examsystem.controller.admin;

import com.monktiger.examsystem.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin/group")
public class GroupController {
@Autowired
private GroupService groupService;

@RequestMapping(method = RequestMethod.GET)
public Map<String,Object> getGroup(HttpServletRequest request){
    return groupService.getGroup(request);
}
    @RequestMapping(value = "{id}",method = RequestMethod.PATCH)
    public Map<String,Object> getGroup(@PathVariable("id")String groupId ,HttpServletRequest request){
        return groupService.updateGroup(groupId,request);
    }
    @RequestMapping(value = "{id}",method = RequestMethod.PATCH)
    public Map<String,Object> deleteGroup(@PathVariable("id")String groupId){
        return groupService.deleteGroup(groupId);
    }
}
