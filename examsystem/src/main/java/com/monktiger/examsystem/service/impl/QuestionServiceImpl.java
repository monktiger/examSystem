package com.monktiger.examsystem.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.monktiger.examsystem.entity.Page;
import com.monktiger.examsystem.entity.Question;
import com.monktiger.examsystem.mapper.QuestionMapper;
import com.monktiger.examsystem.service.QuestionService;
import com.monktiger.examsystem.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Override
    public Map<String, Object> getQuestion(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>();
        String search = HttpServletRequestUtil.getString(request,"search");
        int pageNum = HttpServletRequestUtil.getInt(request,"pageNum");
        if(pageNum==0)pageNum=1;
        int total = questionMapper.getQuestionCount(search);
        Page page = new Page(pageNum,total);
        List<Question> questionList = questionMapper.getTotal(page.getStartIndex(),page.getPageSize(),search);
        modelMap.put("questionList",questionList);
        modelMap.put("pageNum", pageNum);
        modelMap.put("pages", page.getTotalPage());
        modelMap.put("total", page.getTotalRecord());
        modelMap.put("status",1);
        modelMap.put("msg","返回成功");
        return modelMap;
    }

    @Override
    public Map<String, Object> putQuestion(String jsonString) {
        Map<String,Object> modelMap = new HashMap<>();
        JSONObject json = JSON.parseObject(jsonString);
        JSONArray questionArray = json.getJSONArray("questionList");
        List<Question> questionList = questionArray.toJavaList(Question.class);
        for(Question q:questionList){
            questionMapper.insertSelective(q);
        }
        modelMap.put("status",1);
        modelMap.put("msg","插入成功");
        return modelMap;
    }

    @Override
    public Map<String, Object> deleteQuestion(int questionId) {
        Map<String,Object> modelMap = new HashMap<>();
        questionMapper.deleteByPrimaryKey(questionId);
        modelMap.put("status",1);
        modelMap.put("msg","删除成功");
        return modelMap;
    }

    @Override
    public Map<String, Object> updateQuestion(int questionId, String jsonString) {
        Map<String,Object> modelMap = new HashMap<>();
        JSONObject json = JSON.parseObject(jsonString);
        Question question = json.toJavaObject(Question.class);
        question.setId(questionId);
        questionMapper.insertSelective(question);
        modelMap.put("status",1);
        modelMap.put("msg","更新成功");
        return modelMap;
    }
}
