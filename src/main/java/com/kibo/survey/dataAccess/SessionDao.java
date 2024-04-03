package com.kibo.survey.dataAccess;

import com.kibo.survey.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionDao extends JpaRepository<Session, Integer>{

    Session findBySessionAndSurveyLink(String session, String surveyLink);


}
