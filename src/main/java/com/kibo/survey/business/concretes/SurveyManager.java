package com.kibo.survey.business.concretes;

import com.kibo.survey.business.abstracts.SurveyService;
import com.kibo.survey.business.constants.SurveyMessages;
import com.kibo.survey.core.utilities.result.*;
import com.kibo.survey.dataAccess.SurveyDao;
import com.kibo.survey.entities.Question;
import com.kibo.survey.entities.Survey;
import com.kibo.survey.entities.dtos.RequestQuestionDto;
import com.kibo.survey.entities.dtos.RequestSurveyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyManager implements SurveyService {


    private SurveyDao surveyDao;

    @Autowired
    public SurveyManager(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;

    }

    @Override
    public Result addSurvey(Survey survey) {
        if(survey.getName().isEmpty()) {
            return new ErrorResult(SurveyMessages.surveyNameCannotBeNull);
        }


        survey.setSurveyLink(generateSurveyLink());
        survey.setActive(true);
        survey.setCreatedAt(new Date());

        surveyDao.save(survey);
        return new SuccessResult(SurveyMessages.surveyAddSuccess);
    }

    @Override
    public Result updateSurvey(Survey survey) {
        var result = getSurveyById(survey.getId());
        if(!result.isSuccess()){
            return new ErrorResult(result.getMessage());
        }

        var surveyToUpdate = result.getData();
        if(survey.getName().isEmpty()){
            return new ErrorResult(SurveyMessages.surveyNameCannotBeNull);
        }
        if(survey.getQuestions().isEmpty()){
            return new ErrorResult(SurveyMessages.surveyQuestionsCannotBeNull);
        }

        surveyToUpdate.setName(survey.getName());
        surveyToUpdate.setQuestions(survey.getQuestions());
        surveyToUpdate.setActive(survey.isActive());

        surveyDao.save(surveyToUpdate);
        return null;
    }

    @Override
    public Result deleteSurvey(int id) {
        var result = getSurveyById(id);
        if(!result.isSuccess()){
            return new ErrorResult(SurveyMessages.surveyDoesntExist);
        }
        var survey = result.getData();
        surveyDao.delete(survey);


        return new SuccessResult(SurveyMessages.surveyDeleteSuccess);
    }

    @Override
    public DataResult<Survey> getSurveyById(int id) {
        var result = surveyDao.findById(id);
        if(result == null){
            return new SuccessDataResult<>(SurveyMessages.surveyDoesntExist);
        }
        return new SuccessDataResult<>(result, SurveyMessages.getSurveyByIdSuccess);
    }

    @Override
    public DataResult<List<Survey>> getActiveSurvey() {
        var result = surveyDao.findAllByIsActive(true);
        if(result == null){
            return new SuccessDataResult<>(SurveyMessages.noActiveSurveyFound);
        }

        return new SuccessDataResult<List<Survey>>(result, SurveyMessages.getActiveSurveySuccess);
    }

    @Override
    public DataResult<List<Survey>> getSurveys() {
        var result = surveyDao.findAllOrderById();
        if(result.isEmpty()){
            return new ErrorDataResult<>(SurveyMessages.getSurveysEmpty);
        }

        return new SuccessDataResult<>(result, SurveyMessages.getSurveysSuccess);
    }

    private String generateSurveyLink() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().encodeToString(randomBytes);
    }

    @Override
    public DataResult<Survey> getSurveyByLink(String surveyLink) {
        var result = surveyDao.findBySurveyLinkOrderByCreatedAt(surveyLink);
        if(result == null){
            return new ErrorDataResult<>(SurveyMessages.surveyDoesntExist);
        }

        List<Question> sortedQuestions = result.getQuestions().stream()
                .sorted(Comparator.comparingInt(Question::getQuestionOrder))
                .collect(Collectors.toList());

        result.setQuestions(sortedQuestions);


        return new SuccessDataResult<>(result, SurveyMessages.getSurveyBySurveyLinkSuccess);
    }

    @Override
    public Result changeSurveyName(int id, String newName) {
        var result = getSurveyById(id);
        if(!result.isSuccess()){
            return new ErrorResult(result.getMessage());
        }

        var survey = result.getData();
        survey.setName(newName);
        surveyDao.save(survey);

        return new SuccessResult(SurveyMessages.changeSurveyNameSuccess);
    }

    @Override
    public DataResult<RequestSurveyDto> getSurveyQuestionsByLink(String surveyLink) {
var result = getSurveyByLink(surveyLink);
        if(!result.isSuccess()){
            return new ErrorDataResult<>(result.getMessage());
        }

        var survey = result.getData();

        var requestQuestionDtoList = survey.getQuestions().stream().map(question -> {
            return RequestQuestionDto.builder()
                    .surveyId(survey.getId())
                    .content(question.getContent())
                    .id(question.getId())
                    .build();
        }).toList();

        var requestSurveyDto = RequestSurveyDto.builder()
                .surveyName(survey.getName())
                .questions(requestQuestionDtoList)
                .surveyLink(survey.getSurveyLink())
                .isActive(survey.isActive())
                .createdAt(survey.getCreatedAt())
                .finishDate(survey.getFinishDate())
                .build();


        return new SuccessDataResult<>(requestSurveyDto, SurveyMessages.getSurveyQuestionsByLinkSuccess);
    }

}
