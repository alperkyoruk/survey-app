package com.kibo.survey.WebAPI.controllers;

import com.kibo.survey.business.abstracts.SurveyService;
import com.kibo.survey.core.utilities.result.Result;
import com.kibo.survey.entities.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/surveys" +
        "")
public class SurveyController {
    private SurveyService surveyService;


    public SurveyController(SurveyService surveyService){
        this.surveyService = surveyService;
    }

    @PostMapping("/add")
    public Result addSurvey(@RequestBody Survey survey){
        return surveyService.addSurvey(survey);
    }

    @PostMapping("/update")
    public Result updateSurvey(@RequestBody Survey survey){
        return surveyService.updateSurvey(survey);
    }

    @PostMapping("/delete")
    public Result deleteSurvey(@RequestParam int id){
        return surveyService.deleteSurvey(id);
    }

    @GetMapping("/getSurveyById")
    public Result getSurveyById(@RequestParam int id){
        return surveyService.getSurveyById(id);
    }

    @GetMapping("/getAllSurveys")
    public Result getSurveys(){
        return surveyService.getSurveys();
    }

    @GetMapping("/getActiveSurveys")
    public Result getActiveSurvey(){
        return surveyService.getActiveSurvey();
    }

}
