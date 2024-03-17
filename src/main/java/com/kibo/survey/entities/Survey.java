package com.kibo.survey.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "finish_date")
    private Date finishDate;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "survey_link")
    private String surveyLink;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Question> questions;

}
