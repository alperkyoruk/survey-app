package com.kibo.survey.WebAPI.controllers;

import com.kibo.survey.business.abstracts.RatingService;
import com.kibo.survey.business.abstracts.SessionService;
import com.kibo.survey.business.abstracts.SurveyService;
import com.kibo.survey.core.utilities.result.DataResult;
import com.kibo.survey.core.utilities.result.Result;
import com.kibo.survey.entities.dtos.RequestRatingDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SurveyService surveyService;


    @PostMapping("/add")
    public Result addRating(@RequestBody RequestRatingDto requestRatingDto){
        return ratingService.addRating(requestRatingDto);
    }

    @GetMapping("/getRatingsByQuestionId")
    public Result getRatingById(@RequestParam int questionId){
        return ratingService.getRatingsByQuestionId(questionId);
    }

    @PostMapping("/addRatingList")
    public Result addRatingList(@RequestBody List<RequestRatingDto> requestRatingDtos, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        var surveyLink = surveyService.getSurveyByQuestionId(requestRatingDtos.stream().findFirst().get().getQuestionId());


        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("session")) {
                    sessionService.addSurveyToSession(cookie.getValue(), surveyLink.getData().getSurveyLink());
                }
            }
        }

        return ratingService.addRatingList(requestRatingDtos);
    }

    @GetMapping("/getAverageRatingByQuestionId")
    public DataResult<Float> getAverageRatingByQuestionId(@RequestParam int questionId){
        return ratingService.getAverageRatingByQuestionId(questionId);
    }

}
