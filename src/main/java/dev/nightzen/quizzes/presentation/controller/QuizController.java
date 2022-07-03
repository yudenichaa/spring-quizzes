package dev.nightzen.quizzes.presentation.controller;

import dev.nightzen.quizzes.business.entity.Quiz;
import dev.nightzen.quizzes.business.entity.QuizSolution;
import dev.nightzen.quizzes.business.service.QuizService;
import dev.nightzen.quizzes.presentation.dto.SolveQuizRequestDto;
import dev.nightzen.quizzes.presentation.dto.SolveQuizResponseDto;
import dev.nightzen.quizzes.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz,
                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return quizService.addQuiz(quiz, userDetails.getUser());
    }

    @GetMapping("quizzes/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizService.getQuiz(id);
    }

    @GetMapping("quizzes")
    Page<Quiz> getQuizzes(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size) {
        return quizService.getQuizzes(page, size);
    }

    @PostMapping("quizzes/{id}/solve")
    public SolveQuizResponseDto solveQuiz(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @Valid @RequestBody SolveQuizRequestDto solveQuizRequestDto) {
        return quizService.solveQuiz(id, userDetails.getUser(), solveQuizRequestDto.getAnswer());
    }

    @DeleteMapping("quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable Long id,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        quizService.deleteQuiz(id, userDetails.getUser());
    }

    @GetMapping("quizzes/completed")
    public Page<QuizSolution> getQuizSolutions(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        return quizService.getQuizSolutions(userDetails.getUser(), page, size);
    }
}
