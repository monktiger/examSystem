package com.monktiger.examsystem.util;

import com.monktiger.examsystem.entity.Admin;
import com.monktiger.examsystem.entity.User;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TokenUtil {
    public static String createToken(Admin admin){
        StringBuilder  str=new StringBuilder();
        str.append("token:");
        str.append(MD5Util.getMd5(admin.getUsername(),32)+"-");
        str.append(new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date(System.currentTimeMillis()))+"-");
        Random r = new Random();
        Integer temp =r.nextInt();
        str.append(MD5Util.getMd5(temp.toString(),6));
        return str.toString();
    }
}