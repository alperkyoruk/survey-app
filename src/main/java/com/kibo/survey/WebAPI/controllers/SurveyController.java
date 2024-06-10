package com.kibo.survey.WebAPI.controllers;

import com.kibo.survey.business.abstracts.SessionService;
import com.kibo.survey.business.abstracts.SurveyService;
import com.kibo.survey.core.utilities.result.DataResult;
import com.kibo.survey.core.utilities.result.Result;
import com.kibo.survey.entities.Survey;
import com.kibo.survey.entities.dtos.RequestSurveyDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/surveys")
public class SurveyController {
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private SessionService sessionService;
    @PostMapping("/add")
    public Result addSurvey(@RequestBody Survey survey){
        return surveyService.addSurvey(survey);
    }
    @PostMapping("/update")
    public Result updateSurvey(@RequestBody Survey survey){
        return surveyService.updateSurvey(survey);
    }
    @DeleteMapping("/delete")
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
    @GetMapping("/getSurveyByLink")
    public DataResult<Survey> getSurveyByLink(@RequestParam String surveyLink){
        return surveyService.getSurveyByLink(surveyLink);
    }
    @PostMapping("/changeSurveyName")
    public Result changeSurveyName(@RequestParam int surveyId, @RequestParam String newName){
        return surveyService.changeSurveyName(surveyId, newName);
    }
    @GetMapping("/getSurveyQuestionsByLink")
    public DataResult<RequestSurveyDto> getSurveyQuestionsByLink(@RequestParam String surveyLink){
        return surveyService.getSurveyQuestionsByLink(surveyLink);
    }
    @GetMapping("/checkIfUserSubmittedSurveyBefore")
    public Result isUserSubmittedSurveyBefore(@RequestParam String surveyLink, HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        String session = null;
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("session")) {
                    session = cookie.getValue();
                }
            }
        }
        var sessionResult = sessionService.isUserSubmittedSurveyBeforeIfNotGetSession(session, surveyLink);
        if(sessionResult.getData() != null){
            var cookie = new Cookie("session", sessionResult.getData());
            cookie.setPath("/");
            cookie.setDomain("devrimanket.com");
            cookie.setMaxAge(31536000);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            response.addCookie(cookie);
        }
        return sessionResult;
    }

}
