package dev.nightzen.quizzes.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SolveQuizResponseDto {
    private boolean success;
    private String feedback;
}
