package com.quizapp.quiz_service.dto;

import lombok.Data;

@Data
public class QuizDto {
    private String categoryName;
    private Integer numQuestions;
    private String title;
}
