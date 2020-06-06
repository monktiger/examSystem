package com.monktiger.examsystem.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface QuestionService {
    Map<String, Object> getQuestion(HttpServletRequest request);

    Map<String, Object> putQuestion(String jsonString);

    Map<String, Object> deleteQuestion(int questionId);

    Map<String, Object> updateQuestion(int questionId, String jsonString);
}
