package com.monktiger.examsystem.util;


import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class Encryptor {
    @Test
    public void getPass(){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ0");
        String url = textEncryptor.encrypt("jdbc:mysql://120.27.145.98/db_examsystem?autoReconnect=true&useSSL=false&characterEncoding=utf-8");
        String name = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("VIGO123123");
        String appId = textEncryptor.encrypt("wx44735e1dcba5d758");
        String appSecret = textEncryptor.encrypt("d220ad3dc1015c5f126014084f376eb2");

//解密内容
//        String url = textEncryptor.decrypt("");
//        String name = textEncryptor.decrypt("");
//         password = textEncryptor.decrypt("4EyN0xDLbnP2lsaayjl8fbIctj5bVIdD");


        System.out.println("url:"+url);
        System.out.println("name:"+name);
        System.out.println("password:"+password);
        System.out.println("appId:"+appId);
        System.out.println("appSecret:"+appSecret);
        Assert.assertTrue(name.length() > 0);
        Assert.assertTrue(password.length() > 0);
    }

}
