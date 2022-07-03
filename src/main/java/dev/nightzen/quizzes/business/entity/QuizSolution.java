package dev.nightzen.quizzes.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class QuizSolution {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Column
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "solutionId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @JsonProperty("id")
    public Long getId() {
        return quiz.getId();
    }
}
