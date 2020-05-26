package com.monktiger.examsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
	private final static Logger LOG = LoggerFactory.getLogger(com.monktiger.examsystem.controller.GlobalExceptionHandler.class);
//	@ExceptionHandler(value = Exception.class)
//	@ResponseBody
//	public Map<String, Object> handle(Exception e) {
//		Map<String, Object> modelMap = new HashMap<String, Object>();
//		modelMap.put("status",-1);
//		modelMap.put("msg", "系统内部错误!");
//		return modelMap;
//	}
}
