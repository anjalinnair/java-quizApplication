package com.quizapp.quiz_service.service;

import com.quizapp.quiz_service.dao.QuizDao;
import com.quizapp.quiz_service.exception.ResourceNotFoundException;
import com.quizapp.quiz_service.feign.QuizInterface;
import com.quizapp.quiz_service.model.QuestionWrapper;
import com.quizapp.quiz_service.entity.Quiz;
import com.quizapp.quiz_service.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public Quiz createQuiz(String category, int numQ, String title) {
        log.info("Creating quiz: '{}' (Category: {}, Qty: {})", title, category, numQ);
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        return quizDao.save(quiz);
    }

    public List<Quiz> getAllQuizzes() {
        log.info("Fetching all quizzes");
        return quizDao.findAll();
    }

    public List<QuestionWrapper> getQuizQuestions(Integer id) {
        log.info("Fetching questions for quiz ID: {}", id);
        Quiz quiz = quizDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with ID: " + id));
        List<Integer> questionIds = quiz.getQuestionIds();
        return quizInterface.getQuestionsFromId(questionIds).getBody();
    }

    public Integer calculateResult(Integer id, List<Response> responses) {
        log.info("Calculating result for quiz ID: {}", id);
        quizDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with ID: " + id));
        return quizInterface.getScore(responses).getBody();
    }

    public void deleteQuiz(Integer id) {
        log.info("Deleting quiz with ID: {}", id);
        Quiz quiz = quizDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with ID: " + id));
        quizDao.delete(quiz);
    }
}
