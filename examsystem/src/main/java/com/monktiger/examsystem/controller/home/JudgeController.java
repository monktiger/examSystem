package com.monktiger.examsystem.controller.home;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/judge")
public class JudgeController {
@RequestMapping(value = "/score",method = RequestMethod.GET)
    public Map<String,Object> putScore(HttpServletRequest request){

}
@RequestMapping(value = "/juge",method = RequestMethod.GET)
    public Map<String,Object> putJudge(HttpServletRequest request){

}
}
