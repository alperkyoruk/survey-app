package com.kibo.survey.business.concretes;

import com.kibo.survey.business.abstracts.SessionService;
import com.kibo.survey.business.constants.SessionMessages;
import com.kibo.survey.core.utilities.result.*;
import com.kibo.survey.dataAccess.SessionDao;
import com.kibo.survey.entities.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class SessionManager implements SessionService {

    @Autowired
    private SessionDao sessionDao;


    @Override
    public DataResult<String> isUserSubmittedSurveyBeforeIfNotGetSession(String session, String surveyLink) {
        if (session != null && surveyLink != null) {
            Session sessionToCheck = sessionDao.findBySessionAndSurveyLink(session, surveyLink);

            if (sessionToCheck == null) {
                 return new ErrorDataResult<>(SessionMessages.sessionDoesntSubmittedSurveyBefore);
            }

            return new SuccessDataResult<>(SessionMessages.sessionFoundBefore);

        }

        if(session == null){
            return new ErrorDataResult<String>(generateSession(), SessionMessages.sessionNotFoundBefore);
        }

        return new ErrorDataResult<>(SessionMessages.invalidError);


    }

    @Override
    public Result addSurveyToSession(String session, String surveyLink) {

        var sessionToCheck = sessionDao.findBySessionAndSurveyLink(session, surveyLink);

        if (sessionToCheck != null) {
            return new ErrorResult(SessionMessages.sessionAlreadySubmittedSurvey);
        }

        var sessionToAdd = Session.builder()
                .session(session)
                .surveyLink(surveyLink)
                .build();

        sessionDao.save(sessionToAdd);

        return new SuccessResult(SessionMessages.surveyAddedToSession);

    }

    private String generateSession() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().encodeToString(randomBytes);
    }


}
