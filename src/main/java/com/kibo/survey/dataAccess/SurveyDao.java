package com.kibo.survey.dataAccess;

import com.kibo.survey.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyDao extends JpaRepository<Survey, Integer>{
    Survey findById(int id);
    Survey findByIsActive(boolean isActive);

    List<Survey> findAllByIsActive(boolean isActive);
}
