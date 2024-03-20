package com.kibo.survey.dataAccess;

import com.kibo.survey.core.utilities.result.DataResult;
import com.kibo.survey.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RatingDao extends JpaRepository<Rating, Integer> {
    Rating findById(int id);

    List<Rating> findAllByQuestionId(int questionId);

    @Query("SELECT AVG(rating) FROM Rating WHERE question.id = :questionId")
    Float getAvgRatingOfQuestion(int questionId);
    



}
