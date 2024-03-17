package com.kibo.survey.business.concretes;

import com.kibo.survey.business.abstracts.QuestionService;
import com.kibo.survey.business.abstracts.RatingService;
import com.kibo.survey.business.constants.RatingMessages;
import com.kibo.survey.core.utilities.result.*;
import com.kibo.survey.dataAccess.RatingDao;
import com.kibo.survey.entities.Question;
import com.kibo.survey.entities.Rating;
import com.kibo.survey.entities.dtos.RequestRatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RatingManager implements RatingService {


    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private QuestionService questionService;

    @Override
    public DataResult<List<Rating>> getAllRatings() {
        var result = ratingDao.findAll();

        return new SuccessDataResult<List<Rating>>(result, "mesaj");
    }

    @Override
    public Result addRating(RequestRatingDto requestRatingDto) {
        if(requestRatingDto.getRating() == 0){
            return new ErrorDataResult<>(RatingMessages.ratingCannotBeNull);
        }
        if(requestRatingDto.getQuestionId()==0){
            return new ErrorDataResult<>(RatingMessages.ratingQuestionIdCannotBeNull);

        }
        if(requestRatingDto.getRating() < 1 || requestRatingDto.getRating() > 5){
            return new ErrorDataResult<>(RatingMessages.ratingValueMustBeBetween1And5);
        }

        var questionResponse = questionService.findById(requestRatingDto.getQuestionId());
        if(!questionResponse.isSuccess()){
            return new ErrorDataResult<>(questionResponse.getMessage());
        }

        var rating = Rating.builder()
                .rating(requestRatingDto.getRating())
                .question(questionResponse.getData())
                .submittedAt(new Date())
                .build();

        ratingDao.save(rating);
        return new SuccessDataResult<>(RatingMessages.ratingAddSuccess);
    }

    @Override
    public DataResult<List<Rating>> getRatingsByQuestionId(int questionId) {

        var result = ratingDao.findAllByQuestionId(questionId);
        return new SuccessDataResult<List<Rating>>(result, RatingMessages.getRatingsByQuestionIdSuccess);


    }

    @Override
    public Result addRatingList(List<RequestRatingDto> requestRatingDtos) {
        for (var rating:requestRatingDtos){
            var addResult = addRating(rating);
            if(!addResult.isSuccess()){
                return addResult;
            }
        }

        return new SuccessResult(RatingMessages.ratingAddSuccess);
    }

    public DataResult<Float> getAverageRatingByQuestionId(int questionId) {
       var result = ratingDao.getAvgRatingOfQuestion(questionId);

       return new SuccessDataResult<Float>(result, RatingMessages.getAverageRatingByQuestionIdSuccess);

    }


}
