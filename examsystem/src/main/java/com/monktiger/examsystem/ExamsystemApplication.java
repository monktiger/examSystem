package com.monktiger.examsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;

@SpringBootApplication
public class ExamsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamsystemApplication.class, args);
    }

}
