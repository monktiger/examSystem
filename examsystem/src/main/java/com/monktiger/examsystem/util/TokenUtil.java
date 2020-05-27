package com.monktiger.examsystem.util;

import com.monktiger.examsystem.entity.User;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TokenUtil {
    public static String createToken(User user,int type) throws UnsupportedEncodingException {
        StringBuilder  str=new StringBuilder();
        if(type==1){
        str.append("token:");}
        else str.append("check:");
        str.append(MD5Util.getMd5(user.getName(),32)+"-");
        str.append(user.getOpenId().toString()+"-");
        str.append(new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date(System.currentTimeMillis()))+"-");
        Random r = new Random();
        Integer temp =r.nextInt();
        str.append(MD5Util.getMd5(temp.toString(),6));
        return str.toString();
    }
}