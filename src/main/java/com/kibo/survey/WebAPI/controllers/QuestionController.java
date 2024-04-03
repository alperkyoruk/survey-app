package com.kibo.survey.WebAPI.controllers;

import com.kibo.survey.business.abstracts.QuestionService;
import com.kibo.survey.core.utilities.result.Result;
import com.kibo.survey.entities.dtos.RequestQuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;



    @PostMapping("/add")
    public Result addQuestion(@RequestBody RequestQuestionDto requestQuestionDto){
        return questionService.addQuestion(requestQuestionDto);
    }

    @PostMapping("/delete")
    public Result deleteQuestion(@RequestParam int id){
        return questionService.deleteQuestion(id);
    }

    @GetMapping("/getQuestionById")
    public Result getQuestionById(@RequestParam int id){
        return questionService.findById(id);
    }

    @GetMapping("/getQuestionsBySurveyId")
    public Result getQuestionsBySurveyId(@RequestParam int surveyId){
        return questionService.findAllBySurveyId(surveyId);
    }

    @PostMapping("/update")
    public Result updateQuestion(@RequestBody RequestQuestionDto requestQuestionDto){
        return questionService.updateQuestion(requestQuestionDto);
    }

    @PostMapping("/changeQuestionName")
    public Result changeQuestionName(@RequestParam int questionId, @RequestParam String newName){
        return questionService.changeQuestionName(questionId, newName);
    }

}
