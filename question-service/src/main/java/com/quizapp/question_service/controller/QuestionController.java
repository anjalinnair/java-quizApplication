package com.quizapp.question_service.controller;

import com.quizapp.question_service.entity.Question;
import com.quizapp.question_service.dto.QuestionRequestDto;
import com.quizapp.question_service.dto.QuestionResponseDto;
import com.quizapp.question_service.model.QuestionWrapper;
import com.quizapp.question_service.model.Response;
import com.quizapp.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("allQuestions")
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestions() {
        return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByCategory(@PathVariable String category) {
        return new ResponseEntity<>(questionService.getQuestionsByCategory(category), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<QuestionResponseDto> getQuestionById(@PathVariable Integer id) {
        return new ResponseEntity<>(questionService.getQuestionById(id), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<QuestionResponseDto> addQuestion(@RequestBody QuestionRequestDto questionRequestDto) {
        return new ResponseEntity<>(questionService.addQuestion(questionRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<QuestionResponseDto> updateQuestion(@PathVariable Integer id, @RequestBody QuestionRequestDto questionRequestDto) {
        return new ResponseEntity<>(questionService.updateQuestion(id, questionRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        questionService.deleteQuestion(id);
        return new ResponseEntity<>("Question deleted successfully", HttpStatus.OK);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String categoryName, @RequestParam Integer numQuestions) {
        return new ResponseEntity<>(questionService.getQuestionsForQuiz(categoryName, numQuestions), HttpStatus.OK);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds) {
        return new ResponseEntity<>(questionService.getQuestionsFromId(questionIds), HttpStatus.OK);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return new ResponseEntity<>(questionService.getScore(responses), HttpStatus.OK);
    }
}
