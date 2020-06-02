package com.monktiger.examsystem.controller.admin;

import com.monktiger.examsystem.entity.Admin;
import com.monktiger.examsystem.mapper.AdminMapper;
import com.monktiger.examsystem.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class   AdminController {
    @Autowired
    private AdminMapper adminMapper;
    @RequestMapping("/login")
    public Map<String,Object> adminLogin(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        String username = HttpServletRequestUtil.getString(request,"username");
        String password = HttpServletRequestUtil.getString(request,"password");
        Admin admin=adminMapper.checkUser(username,password);
        if(admin!=null){
            request.getSession().setAttribute("admin",admin);
        }else {
            modelMap.put("status",0);
            modelMap.put("msg","登录失败");
            return modelMap;
        }
        modelMap.put("status",1);
        modelMap.put("msg","登录成功");
        return modelMap;
    }

}
