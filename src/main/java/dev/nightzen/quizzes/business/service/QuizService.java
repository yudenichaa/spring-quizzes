package dev.nightzen.quizzes.business.service;

import dev.nightzen.quizzes.business.entity.Quiz;
import dev.nightzen.quizzes.business.entity.QuizSolution;
import dev.nightzen.quizzes.business.entity.User;
import dev.nightzen.quizzes.persistance.QuizRepository;
import dev.nightzen.quizzes.persistance.QuizSolutionRepository;
import dev.nightzen.quizzes.presentation.dto.SolveQuizResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    QuizSolutionRepository quizSolutionRepository;

    public Quiz addQuiz(Quiz quiz, User user) {
        if (quiz.getAnswer() == null) {
            quiz.setAnswer(List.of());
        }

        quiz.setAuthor(user);
        Collections.sort(quiz.getAnswer());
        return quizRepository.save(quiz);
    }

    public Quiz getQuiz(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);

        if (quiz.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return quiz.get();
    }

    public Page<Quiz> getQuizzes(int page, int size) {
        return quizRepository.findAll(PageRequest.of(page, size));
    }

    public SolveQuizResponseDto solveQuiz(Long id, User user, List<Integer> answer) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);

        if (optionalQuiz.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Quiz quiz = optionalQuiz.get();
        Collections.sort(answer);

        if (answer.equals(quiz.getAnswer())) {
            QuizSolution quizSolution = new QuizSolution();
            quizSolution.setUser(user);
            quizSolution.setQuiz(quiz);
            quizSolution.setCompletedAt(LocalDateTime.now());
            quizSolutionRepository.save(quizSolution);
            return new SolveQuizResponseDto(true, "Congratulations, you're right!");
        } else {
            return new SolveQuizResponseDto(false, "Wrong answer! Please, try again.");
        }
    }

    public void deleteQuiz(Long id, User user) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);

        if (optionalQuiz.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Quiz quiz = optionalQuiz.get();

        if (!Objects.equals(user.getId(), quiz.getAuthor().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        quizRepository.delete(quiz);
    }

    public Page<QuizSolution> getQuizSolutions(User user, int page, int size) {
        return quizSolutionRepository.findByUserId(
                user.getId(),
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "completedAt")));
    }
}
