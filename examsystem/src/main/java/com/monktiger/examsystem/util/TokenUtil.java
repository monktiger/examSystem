package com.monktiger.examsystem.util;

import com.bluemsun.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TokenUtil {
    public static String createToken(User user,int type){
        StringBuilder  str=new StringBuilder();
        if(type==1){
        str.append("token:");}
        else str.append("check:");
        str.append(MD5Util.getMd5(user.getUserName(),32)+"-");
        str.append(user.getUserId().toString()+"-");
        str.append(new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date(System.currentTimeMillis()))+"-");
        Random r = new Random();
        Integer temp =r.nextInt();
        str.append(MD5Util.getMd5(temp.toString(),6));
        return str.toString();
    }
}