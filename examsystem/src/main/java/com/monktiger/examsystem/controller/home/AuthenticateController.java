package com.monktiger.examsystem.controller.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.monktiger.examsystem.cache.JedisUtil;
import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.service.UserService;
import com.monktiger.examsystem.service.impl.UserServiceImpl;
import com.monktiger.examsystem.util.TokenUtil;
import com.monktiger.examsystem.utils.resultHander.CommonErrorEnum;
import com.monktiger.examsystem.utils.resultHander.ResponseBean;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RequestMapping("/user")
@RestController
@CrossOrigin
public class AuthenticateController {

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appSecret}")
    private String appSecret;

    private String openId;

    private String sessionKey;

    private String accessToken;

    private Map<String,Object> modelMap = new HashMap<>();

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private JedisUtil.Strings jedisUtilString;
    @Autowired
    private JedisUtil.Keys jedisUtilKey;



    @RequestMapping("/testRedis")
    public String testAdd(String test){
        jedisUtilString.set("test",test);
        return jedisUtilString.get("test");
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    /**
     * 修改个人信息
     * @param name
     * @param nickname
     * @return
     * @throws Exception
     */
    @RequestMapping(value= "/nodify" ,method = RequestMethod.GET)
    public Object nodify(String name, String nickname, HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        if(token!=null&&jedisUtilKey.exists("token"))
        {
            openId = token;
            User user = userService.selectUserByKey(openId);
            if(name!=null)
            user.setName(URLEncoder.encode(name,"utf-8"));
            if(nickname!=null)
            user.setNickname(nickname);
            int state = userService.updateUser(user);
            if(state>=0){
                modelMap.put("status",1);
                modelMap.put("msg","修改成功");
                jedisUtilString.set(openId,JSON.toJSONString(user));
            }else{
                modelMap.put("status",-1);
                modelMap.put("msg","修改失败");
            }
        }
        else{
            modelMap.put("status",0);
            modelMap.put("msg","请登录");
        }
        return modelMap;
    }

    /**
     * 登录 操作
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String,Object> login(String code, String headPic, String userName)throws Exception{

        /**
         * 创建 httpclient 对象
         */
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code";

        try {
            /**
             * 创建url
             */
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();

            /**
             * 创建 http GET请求
             */
            HttpGet httpGet = new HttpGet(uri);

            /**
             * 执行请求
             */
            response = httpClient.execute(httpGet);

            /**
             * 判断返回请求是否为 200
             */
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 解析 resultString
         */
        JSONObject jsonObject = (JSONObject)JSONObject.parse(resultString);
        sessionKey = (String) jsonObject.get("session_key");
        openId = (String) jsonObject.get("openid");
        User user = userService.selectUserByKey(openId);


        /**
         * 第一次使用的用户
         */
        if(user==null){
            jedisUtilString.set("openId",openId);
            User addUser = new User(openId,"无昵称",URLEncoder.encode(userName,"UTF-8"),headPic,true);
            userService.insertUser(addUser);
            jedisUtilString.set(openId, JSON.toJSONString(addUser));
            modelMap.put("userInfo",addUser);
        }else{
            /**
             *  已经授权过的用户
             */
            user.setName(URLDecoder.decode(user.getName(),"utf-8" ));
            jedisUtilString.set(openId, JSON.toJSONString(user));
            modelMap.put("userInfo",user);
        }
        jedisUtilString.set("token",openId);
        modelMap.put("token",openId);
        modelMap.put("status",1);
        modelMap.put("msg","登陆成功");
        return modelMap;
    }

    /**
     * 未授权情况下 进行授权操作
     * @param nickName
     * @param avatarUrl
     * @param name
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Object register(String nickName, String avatarUrl,String name) throws Exception {

        openId = jedisUtilString.get("openId");

        User user = new User(nickName,name,openId,avatarUrl,true);

        /**
         * 注册用户
         */
        if(userService.register(user)==1){
            return new ResponseBean<>(true,"注册成功",CommonErrorEnum.SUCCESS_OPTION);
        }
        return new ResponseBean<>(false,"重复注册", CommonErrorEnum.REPEAT_REGISTER);
    }
}
