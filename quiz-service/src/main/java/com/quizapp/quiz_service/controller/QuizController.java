package com.quizapp.quiz_service.controller;

import com.quizapp.quiz_service.model.QuestionWrapper;
import com.quizapp.quiz_service.dto.QuizDto;
import com.quizapp.quiz_service.entity.Quiz;
import com.quizapp.quiz_service.model.Response;
import com.quizapp.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDto quizDto) {
        Quiz quiz = quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return new ResponseEntity<>(quizService.getAllQuizzes(), HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
        return new ResponseEntity<>(quizService.getQuizQuestions(id), HttpStatus.OK);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return new ResponseEntity<>(quizService.calculateResult(id, responses), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Integer id) {
        quizService.deleteQuiz(id);
        return new ResponseEntity<>("Quiz deleted successfully", HttpStatus.OK);
    }
}
