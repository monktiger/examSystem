package com.monktiger.examsystem.controller.home;

import com.alibaba.fastjson.JSONObject;
import com.monktiger.examsystem.cache.JedisUtil;
import com.monktiger.examsystem.entity.Group;
import com.monktiger.examsystem.entity.User;
import com.monktiger.examsystem.mapper.GroupMapper;
import com.monktiger.examsystem.mapper.UserMapper;
import com.monktiger.examsystem.service.impl.GroupServiceImpl;
import com.monktiger.examsystem.util.MD5Util;
import com.monktiger.examsystem.utils.resultHander.CommonErrorEnum;
import com.monktiger.examsystem.utils.resultHander.ResponseBean;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/group")
@RestController
@CrossOrigin
public class GroupController {

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appSecret}")
    private String appSecret;

    private String openId;

    private String sessionKey;

    private String accessToken;

    @Autowired
    private GroupServiceImpl groupService;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserMapper userMapper;

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
            }else if(state==-1) {
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
//            List<Group> groups = groupMapper.selectByKeyState(groupId,1);
//            modelMap.put("memberList",groups);
            List<Group> groups = groupMapper.selectByKeyState(groupId,1);
            if(groups!=null) {
                List<User> users = userMapper.selectByIds(groups);
                modelMap.put("memberList", users);
                modelMap.put("state", 1);
                modelMap.put("msg", "成功显示");
            }else{
                modelMap.put("state",-1);
                modelMap.put("msg","小组无任何成员");
            }
        }else{
            modelMap.put("state",0);
            modelMap.put("msg","请登录后操作");
        }
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

    /**
     *  组长解散群 组员退出群
     * @param groupId
     * @return
     * @throws Exception
     */
    @RequestMapping("/nodify")
    public Map<String,Object> nodify(String name,String groupId,HttpServletRequest request)throws Exception{
        String token = request.getHeader("token");
        if(token!=null&&jedisUtilKeys.exists("token"))
        {
            String openId = token;
            Group group = groupService.selectGroupById(openId,groupId);

            /**
             * 判断 执行操作的是组长 还是 组员
             */
            if(group.getStatus() == 0){
                if(name!=null)
                    group.setName(name);
                List<Group> groupList = groupService.listGroup(groupId,1);
                groupService.updateGroup(group);

                /**
                 *  同时修改小组成员的组名
                 */
                for(Group group1:groupList){
                    group1.setName(name);
                    groupService.updateGroup(group1);
                }
                modelMap.put("status",1);
                modelMap.put("msg","修改成功");
            }else{
                groupService.dissolve(groupId);
                modelMap.put("status",0);
                modelMap.put("msg","修改失败，非组长");
            }
        }else{
            modelMap.put("status",0);
            modelMap.put("msg","请登录后操作");
        }

        return modelMap;
    }


    @RequestMapping("/QRCode")
    public Map<String,Object> getQRCode(String groupId) throws UnsupportedEncodingException {

        String getAccessToken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appSecret;
        String getTicket = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
        String json = "" +
                "{\"expire_seconds\": 604800, " +
                "\"action_name\": \"QR_STR_SCENE\", " +
                "\"action_info\": {\"scene\": {\"groupId\": "+groupId+"}}} ";
        String QRUel = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

        /**
         * 解析 resultString 获得 accessToken
         */
        JSONObject jsonObject = (JSONObject)JSONObject.parse(GetAction(getAccessToken));
        String accessToken = (String)jsonObject.get("access_token");
        getTicket = getTicket+accessToken;

        System.out.println(getTicket);

        /**
         * 解析 resultString 获得 ticket expire_seconds url
         */
         jsonObject = (JSONObject)JSONObject.parse(PostAction(getTicket ,json));
        String ticket = (String) jsonObject.get("ticket");
        String expire_seconds = (String) jsonObject.get("expire_seconds");
        String url = (String)jsonObject.get("url");


        modelMap.put("expire_seconds",expire_seconds);
        if(url!=null&&ticket!=null)
        modelMap.put("url",url+URLEncoder.encode(ticket,"utf-8"));

        return modelMap;
    }

    private static String GetAction(String url){
        String resultString=null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
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


        return resultString;
    }

    private static String PostAction(String url,String json){
        String resultString=null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        /**
         * 获取二维码
         */
        try {
            /**
             * 创建url
             */
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();

            /**
             * 创建 http POST请求
             */
            HttpPost httpPost = new HttpPost(url);


            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            /**
             * 执行请求
             */
            response = httpClient.execute(httpPost);

            /**
             * 判断返回请求是否为 200
             */
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }


}


