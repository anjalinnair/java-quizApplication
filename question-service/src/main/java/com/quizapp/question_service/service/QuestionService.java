package com.quizapp.question_service.service;

import com.quizapp.question_service.dao.QuestionDao;
import com.quizapp.question_service.exception.ResourceNotFoundException;
import com.quizapp.question_service.entity.Question;
import com.quizapp.question_service.dto.QuestionRequestDto;
import com.quizapp.question_service.dto.QuestionResponseDto;
import com.quizapp.question_service.model.QuestionWrapper;
import com.quizapp.question_service.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public List<QuestionResponseDto> getAllQuestions() {
        log.info("Fetching all questions");
        return questionDao.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<QuestionResponseDto> getQuestionsByCategory(String category) {
        log.info("Fetching questions for category: {}", category);
        return questionDao.findByCategory(category).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public QuestionResponseDto getQuestionById(Integer id) {
        log.info("Fetching question with ID: {}", id);
        Question question = questionDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + id));
        return convertToResponseDto(question);
    }

    public QuestionResponseDto addQuestion(QuestionRequestDto requestDto) {
        log.info("Adding a new question: {}", requestDto.getQuestionTitle());
        Question question = new Question();
        question.setQuestionTitle(requestDto.getQuestionTitle());
        question.setOption1(requestDto.getOption1());
        question.setOption2(requestDto.getOption2());
        question.setOption3(requestDto.getOption3());
        question.setOption4(requestDto.getOption4());
        question.setRightAnswer(requestDto.getRightAnswer());
        question.setDifficultyLevel(requestDto.getDifficultyLevel());
        question.setCategory(requestDto.getCategory());
        Question saved = questionDao.save(question);
        return convertToResponseDto(saved);
    }

    public QuestionResponseDto updateQuestion(Integer id, QuestionRequestDto requestDto) {
        log.info("Updating question with ID: {}", id);
        Question question = questionDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + id));

        question.setQuestionTitle(requestDto.getQuestionTitle());
        question.setOption1(requestDto.getOption1());
        question.setOption2(requestDto.getOption2());
        question.setOption3(requestDto.getOption3());
        question.setOption4(requestDto.getOption4());
        question.setRightAnswer(requestDto.getRightAnswer());
        question.setDifficultyLevel(requestDto.getDifficultyLevel());
        question.setCategory(requestDto.getCategory());

        Question saved = questionDao.save(question);
        return convertToResponseDto(saved);
    }

    public void deleteQuestion(Integer id) {
        log.info("Deleting question with ID: {}", id);
        Question question = questionDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + id));
        questionDao.delete(question);
    }

    public List<Integer> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        log.info("Generating {} random questions for category: {}", numQuestions, categoryName);
        return questionDao.findRandomQuestionsByCategory(categoryName, numQuestions);
    }

    public List<QuestionWrapper> getQuestionsFromId(List<Integer> questionIds) {
        log.info("Fetching question wrappers for IDs: {}", questionIds);
        List<QuestionWrapper> wrappers = new ArrayList<>();
        
        for (Integer id : questionIds) {
            Question question = questionDao.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + id));
            
            wrappers.add(new QuestionWrapper(
                    question.getId(),
                    question.getQuestionTitle(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4()
            ));
        }
        return wrappers;
    }

    public Integer getScore(List<Response> responses) {
        log.info("Calculating score for submissions");
        int right = 0;

        for (Response response : responses) {
            Question question = questionDao.findById(response.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + response.getId()));
            if (response.getResponse().equals(question.getRightAnswer())) {
                right++;
            }
        }
        return right;
    }

    private QuestionResponseDto convertToResponseDto(Question q) {
        return new QuestionResponseDto(
                q.getId(),
                q.getQuestionTitle(),
                q.getOption1(),
                q.getOption2(),
                q.getOption3(),
                q.getOption4(),
                q.getDifficultyLevel(),
                q.getCategory()
        );
    }
}
