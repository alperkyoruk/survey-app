package com.kibo.survey.business.abstracts;

import com.kibo.survey.core.utilities.result.DataResult;
import com.kibo.survey.core.utilities.result.Result;
import com.kibo.survey.entities.Question;
import com.kibo.survey.entities.dtos.RequestQuestionDto;

import java.util.List;

public interface QuestionService {
    DataResult<Question> findById(int id);
    Result addQuestion(RequestQuestionDto requestQuestionDto);
    Result deleteQuestion(int questionId);
    Result updateQuestion(RequestQuestionDto requestQuestionDto);

    DataResult<List<Question>> findAllBySurveyId(int surveyId);

}
