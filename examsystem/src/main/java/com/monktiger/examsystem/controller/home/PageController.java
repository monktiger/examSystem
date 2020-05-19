package com.monktiger.examsystem.controller.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.monktiger.examsystem.cache.JedisUtil;
import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/in")
public class PageController {
    @Autowired
    private JedisUtil.Strings jedisUtilStrings;
    @Autowired
    private JedisUtil.Keys jedisUtilKeys;
@RequestMapping(value = "/toAdminShow",method = RequestMethod.GET)
    public Map<String,Object> toAdminShow(@RequestParam("examId")int examId, HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<>();
    String token = request.getHeader("token");
    if(token!=null){
        String userStirng = jedisUtilStrings.get(token);
        JSONObject userJson = JSON.parseObject(userStirng);
        User user = userJson.toJavaObject(User.class);

    }
        else{modelMap.put("status",0);
            modelMap.put("msg","未登录");
    }return modelMap;
}
@RequestMapping(value = "/toScoreList",method = RequestMethod.GET)
    public Map<String,Object> toScoreList(@RequestParam("examId")int examId,@RequestParam("groupId")String groupId, HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<>();
    return modelMap;
}
@RequestMapping(value = "/toCopy",method = RequestMethod.GET)
    public Map<String,Object> toCopy(@RequestParam("examId")int examId,HttpServletRequest request){
     Map<String,Object> modelMap = new HashMap<>();
    return modelMap;
}
@RequestMapping(value = "/toWrongBook" ,method = RequestMethod.GET)
    public Map<String,Object> toWrongBook(HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<>();
    Integer copyId = HttpServletRequestUtil.getInt(request,"copyId");
    Integer examId = HttpServletRequestUtil.getInt(request,"examId");
    return modelMap;
}
}
