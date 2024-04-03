package com.kibo.survey.business.abstracts;

import com.kibo.survey.core.utilities.result.DataResult;
import com.kibo.survey.core.utilities.result.Result;

public interface SessionService {

    DataResult<String> isUserSubmittedSurveyBeforeIfNotGetSession(String session, String surveyLink);

    Result addSurveyToSession(String session, String surveyLink);

}
