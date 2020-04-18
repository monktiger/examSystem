package com.monktiger.examsystem.util;


import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class Encryptor {
    @Test
    public void getPass(){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ0");
        String url = textEncryptor.encrypt("jdbc:oracle:thin:xxxx");
        String name = textEncryptor.encrypt("数据库账号");
        String password = textEncryptor.encrypt("数据库密码");
//解密内容
//        String url = textEncryptor.decrypt("");
//        String name = textEncryptor.decrypt("");
//        String password = textEncryptor.decrypt("4EyN0xDLbnP2lsaayjl8fbIctj5bVIdD");


        System.out.println(url + "----------------");
        System.out.println(name + "----------------");
        System.out.println(password + "----------------");
        Assert.assertTrue(name.length() > 0);
        Assert.assertTrue(password.length() > 0);
    }

}
