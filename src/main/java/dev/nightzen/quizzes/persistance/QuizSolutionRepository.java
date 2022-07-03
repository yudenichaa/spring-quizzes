package dev.nightzen.quizzes.persistance;

import dev.nightzen.quizzes.business.entity.QuizSolution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizSolutionRepository extends PagingAndSortingRepository<QuizSolution, Long> {
    Page<QuizSolution> findByUserId(Long id, Pageable pageable);
}
