package com.monktiger.examsystem.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.monktiger.examsystem.cache.JedisUtil;
import com.monktiger.examsystem.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class AdminControl extends HandlerInterceptorAdapter {
    /**
     * 主要做事前拦截，即用户操作发生前，改写preHandle里的逻辑，进行拦截
     */
    @Autowired
    private JedisUtil.Keys jedisUtilKeys;
    @Autowired
    private JedisUtil.Strings jedisUtilStrings;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("token");
        if (token!=null) {
            if (jedisUtilKeys.exists(token))
            {
                String userString = jedisUtilStrings.get(token);
                JSONObject userJSON = JSON.parseObject(userString);
                Admin user = JSON.toJavaObject(userJSON, Admin.class);
                jedisUtilStrings.setEx(token, 2 * 60 * 60, userString);
                // 做空值判断，确保userId不为空并且该帐号的可用状态为
                if (user != null)
                    // 若通过验证则返回true,拦截器返回true之后，用户接下来的操作得以正常执行
                    return true;}
        }
        // 若不满足登录验证，则直接跳转到帐号登录页面
        PrintWriter out = response.getWriter();
        out.print("noLogin");
        response.setStatus(401);
        out.flush();
        return false;
    }
}
