package com.kibo.survey.dataAccess;

import com.kibo.survey.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionDao extends JpaRepository<Question, Integer> {
    Question findById(int id);
    List<Question> findAllBySurveyIdOrderByQuestionOrder(int surveyId);


}
