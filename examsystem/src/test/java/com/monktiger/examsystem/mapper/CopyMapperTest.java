package com.monktiger.examsystem.mapper;

import com.monktiger.examsystem.entity.Copy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CopyMapperTest {
    @Autowired
    CopyMapper copyMapper;

    @Test
    public void listAll(){
        List<Copy> copyList = copyMapper.selectByGroupAndExam(1,"math_group");
        for(Copy copy:copyList){
            System.out.println(copy.toString());
        }
    }

    @Test
    public void insert(){
        Copy copy = new Copy(1,"math_group",1,"open_id",0,"有问题哈小伙子",0);
        copyMapper.insert(copy);
    }

    @Test
    public void deleteByPrimaryKey(){
        copyMapper.deleteByPrimaryKey(2);
    }

    @Test
    public void updateJudge(){
        copyMapper.updateJudge(1,"updateJudge");
    }

    @Test
    public void update(){
        Copy copy = new Copy(1,"update_group",1,"open_id",0,"有问题哈小伙子",0);
        copyMapper.updateByPrimaryKey(copy);
    }

    @Test
    public void select(){
        Copy copy = copyMapper.selectByPrimaryKey(1);
        System.out.println(copy.toString());
    }
}
