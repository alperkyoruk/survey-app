package com.kibo.survey.dataAccess;

import com.kibo.survey.core.utilities.result.DataResult;
import com.kibo.survey.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingDao extends JpaRepository<Rating, Integer> {
    Rating findById(int id);

    List<Rating> findAllByQuestionId(int questionId);
    



}
