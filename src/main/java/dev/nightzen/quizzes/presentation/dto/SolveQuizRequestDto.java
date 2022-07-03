package dev.nightzen.quizzes.presentation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SolveQuizRequestDto {
    private List<Integer> answer = new ArrayList<>();
}
