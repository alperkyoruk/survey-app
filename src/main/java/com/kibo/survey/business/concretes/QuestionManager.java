package com.kibo.survey.business.concretes;

import com.kibo.survey.business.abstracts.QuestionService;
import com.kibo.survey.business.abstracts.SurveyService;
import com.kibo.survey.business.constants.QuestionMessages;
import com.kibo.survey.core.utilities.result.*;
import com.kibo.survey.dataAccess.QuestionDao;
import com.kibo.survey.entities.Question;
import com.kibo.survey.entities.dtos.RequestQuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionManager implements QuestionService {


    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private SurveyService surveyService;


    @Override
    public DataResult<Question> findById(int id) {
        var result = questionDao.findById(id);
        if(result == null ){
            return new SuccessDataResult<>(QuestionMessages.questionCannotBeFound);
        }

        return new SuccessDataResult<>(result, QuestionMessages.getQuestionByIdSuccess);
    }

    @Override
    public DataResult<List<Question>> findAllBySurveyId(int surveyId) {
        var  result = questionDao.findAllBySurveyIdOrderByQuestionOrder(surveyId);
        if(result == null){
            return new ErrorDataResult<>(QuestionMessages.questionCannotBeFound);
        }

        return new SuccessDataResult<>(result, QuestionMessages.getQuestionByIdSuccess);
    }

    @Override
    public Result changeQuestionName(int questionId, String newName) {
        var result = findById(questionId);
        if(!result.isSuccess()){
            return new ErrorResult(result.getMessage());
        }
        var question = result.getData();
        question.setContent(newName);
        questionDao.save(question);
        return new SuccessResult(QuestionMessages.questionUpdateSuccess);
    }

    @Override
    public Result addQuestion(RequestQuestionDto requestQuestionDto) {
        if(requestQuestionDto.getContent().isEmpty()){
            return new ErrorDataResult<>(QuestionMessages.questionContentCannotBeNull);
        }
        if(requestQuestionDto.getSurveyId()==0){
            return new ErrorDataResult<>(QuestionMessages.questionSurveyIdCannotBeNull);
        }

        var surveyResponse = surveyService.getSurveyById(requestQuestionDto.getSurveyId());

        if(!surveyResponse.isSuccess()){
            return new ErrorResult(surveyResponse.getMessage());
        }

        int questionsOrder = surveyResponse.getData().getQuestions().size() + 1;

        var question = Question.builder()
                .content(requestQuestionDto.getContent())
                .survey(surveyResponse.getData())
                .questionOrder(questionsOrder)
                .build();



        questionDao.save(question);

        return new SuccessResult(QuestionMessages.questionAddSuccess);
    }


    @Override
    public Result deleteQuestion(int questionId) {
        var question = questionDao.findById(questionId);
        if(question == null){
            return new ErrorResult(QuestionMessages.questionDoesntExist);
        }

        questionDao.delete(question);
        return new SuccessResult(QuestionMessages.questionDeleteSuccess);
    }

    @Override
    public Result updateQuestion(RequestQuestionDto requestQuestionDto) {
        var question = questionDao.findById(requestQuestionDto.getSurveyId());
        if(question == null){
            return new ErrorResult(QuestionMessages.questionDoesntExist);
        }

        question.setContent(requestQuestionDto.getContent());
        questionDao.save(question);
        return null;
    }

}
