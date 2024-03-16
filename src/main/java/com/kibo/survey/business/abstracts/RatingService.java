package com.kibo.survey.business.abstracts;

import com.kibo.survey.core.utilities.result.DataResult;
import com.kibo.survey.core.utilities.result.Result;
import com.kibo.survey.entities.Rating;
import com.kibo.survey.entities.dtos.RequestRatingDto;

import java.util.List;

public interface RatingService {

    DataResult<List<Rating>> getAllRatings();

    Result addRating(RequestRatingDto requestRatingDto);

    DataResult<List<Rating>> getRatingsByQuestionId(int questionId);

    Result addRatingList(List<RequestRatingDto> requestRatingDtos);

    DataResult<Double> getAverageRatingByQuestionId(int questionId);

}
