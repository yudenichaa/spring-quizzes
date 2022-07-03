package dev.nightzen.quizzes.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotBlank
    private String title;

    @Column
    @NotBlank
    private String text;

    @Column
    @ElementCollection
    @NotNull
    @Size(min = 2)
    private List<String> options;

    @Column
    @ElementCollection
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "email", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User author;

    @JsonIgnore
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuizSolution> solutions = new ArrayList<>();
}
