package com.kibo.survey.business.concretes;

import com.kibo.survey.business.abstracts.SurveyService;
import com.kibo.survey.business.constants.SurveyMessages;
import com.kibo.survey.core.utilities.result.*;
import com.kibo.survey.dataAccess.SurveyDao;
import com.kibo.survey.entities.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class SurveyManager implements SurveyService {

    @Autowired
    private SurveyDao surveyDao;

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
        var result = surveyDao.findAll();
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
}
