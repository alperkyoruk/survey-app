package com.kibo.survey.business.abstracts;

import com.kibo.survey.core.utilities.result.DataResult;
import com.kibo.survey.core.utilities.result.Result;
import com.kibo.survey.entities.Survey;

import java.util.List;

public interface SurveyService{
    Result addSurvey(Survey survey);
    Result updateSurvey(Survey survey);
    Result deleteSurvey(int surveyId);
    DataResult<Survey> getSurveyById(int surveyId);
    DataResult<List<Survey>>getActiveSurvey();
    DataResult<List<Survey>> getSurveys();

}
