package com.kibo.survey.dataAccess;

import com.kibo.survey.entities.Question;
import com.kibo.survey.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveyDao extends JpaRepository<Survey, Integer>{
    Survey findById(int id);
    Survey findByIsActive(boolean isActive);

    List<Survey> findAllByIsActive(boolean isActive);

    Survey findBySurveyLinkOrderByCreatedAt(String surveyLink);

    @Query("from Survey order by id")
    List<Survey> findAllOrderById();

    Survey findSurveyByQuestions(List<Question> questions);
}
