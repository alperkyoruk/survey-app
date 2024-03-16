package com.kibo.survey.WebAPI.controllers;

import com.kibo.survey.business.abstracts.RatingService;
import com.kibo.survey.core.utilities.result.Result;
import com.kibo.survey.entities.dtos.RequestRatingDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    private RatingService ratingService;

    public RatingController(RatingService ratingService){
        this.ratingService = ratingService;
    }

    @PostMapping("/add")
    public Result addRating(@RequestBody RequestRatingDto requestRatingDto){
        return ratingService.addRating(requestRatingDto);
    }

    @GetMapping("/getRatingsByQuestionId")
    public Result getRatingById(@RequestParam int questionId){
        return ratingService.getRatingsByQuestionId(questionId);
    }

    @PostMapping("/addRatingList")
    public Result addRatingList(@RequestBody List<RequestRatingDto> requestRatingDtos){
        return ratingService.addRatingList(requestRatingDtos);
    }

}
