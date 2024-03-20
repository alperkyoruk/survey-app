package com.kibo.survey.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestSurveyDto {

    private String surveyName;
    private String surveyLink;
    private boolean isActive;
    private Date finishDate;
    private Date createdAt;

    private List<RequestQuestionDto> questions;






}
