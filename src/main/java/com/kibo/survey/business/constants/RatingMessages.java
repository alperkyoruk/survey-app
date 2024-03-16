package com.kibo.survey.business.constants;

import com.kibo.survey.entities.Rating;
import com.kibo.survey.entities.Survey;

import java.util.List;

public class RatingMessages {
    public static String ratingCannotBeNull = "Rating cannot be null";
    public static String ratingQuestionIdCannotBeNull = "Rating question id cannot be null";
    public static String ratingAddSuccess = "Rating has been added";
    public static String getRatingsByQuestionIdSuccess = "Ratings has been found";
    public static String ratingValueMustBeBetween1And5 = "Rating value must be between 1 and 5";
    public static Rating ratingDoesntExist;
}
