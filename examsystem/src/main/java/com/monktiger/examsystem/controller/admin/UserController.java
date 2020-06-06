package com.monktiger.examsystem.controller.admin;

import com.monktiger.examsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
import java.util.Map;

@CrossOrigin
@RequestMapping("/admin/user")
@RestController
public class UserController {
   @Autowired
   private UserService userService;
    @RequestMapping(method = RequestMethod.GET)
    public Map<String,Object> getUser(HttpServletRequest request){
        return userService.getUser(request);
    }
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public Map<String,Object> banUser(@PathVariable("id")String openId){
        return userService.banUser(openId);
    }
    @RequestMapping(value = "{id}",method = RequestMethod.PATCH)
    public Map<String,Object> updateUser(@PathVariable("id")String openId,HttpServletRequest request){
        return userService.updateUser(openId,request);
    }
}
