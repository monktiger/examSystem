package com.monktiger.examsystem.controller.home;

import com.alibaba.fastjson.JSONObject;
import com.monktiger.examsystem.cache.JedisUtil;
import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.service.UserService;
import com.monktiger.examsystem.service.impl.UserServiceImpl;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.net.URI;


@RequestMapping("/user")
@RestController
@CrossOrigin
public class AuthenticateController {

    @Value("wechat.appId")
    private String appId;

    @Value("wecaht.appSecret")
    private String appSecret;

    private String openId;

    private String sessionKey;

    private String accessToken;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    JedisUtil jedisUtil;

    /**
     * 修改个人信息
     * @param name
     * @param nickname
     * @param openId
     * @return
     * @throws Exception
     */
    @RequestMapping("/nodify")
    public Object nodify(String name,String nickname,String openId) throws Exception {
        User user = userService.selectUserByKey(openId);
        user.setName(name);
        user.setNickname(nickname);
        if(userService.updateUser(user)==0){
            return new ResponseBean(true, CommonErrorEnum.SUCCESS_OPTION);
        }else{
            return new ResponseBean(false,CommonErrorEnum.FAILED_QUESTION);
        }
    }

    /**
     * 登录 操作
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping("/login")
    public Object login(String code)throws Exception{

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
            jedisUtil.STRINGS.set("openId",openId);
            return new ResponseBean<>(false,"跳转至注册界面 并执行 授权接口",CommonErrorEnum.LOGIN_FIRST);
        }

        /**
         *  已经授权过的用户
         */
        return null;
    }

    /**
     * 未授权情况下 进行授权操作
     * @param nickName
     * @param avatarUrl
     * @param name
     * @return
     * @throws Exception
     */
    @RequestMapping("/register")
    public Object register(String nickName, String avatarUrl,String name) throws Exception {

        openId = jedisUtil.STRINGS.get("openId");

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
