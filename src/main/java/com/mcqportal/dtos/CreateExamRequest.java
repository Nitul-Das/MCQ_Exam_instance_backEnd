package com.mcqportal.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateExamRequest {

    @NotBlank(message = "Exam title is required")
    private String examTitle;

    @NotNull(message = "Duration is required")
    @Min(value = 15, message = "Duration must be at least 15 minute")
    private Integer duration;

    @NotNull(message = "Total marks is required")
    @Min(value = 10, message = "Total marks must be at least 10")
    private Integer totalMarks;

    @NotNull(message = "Start time is required")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @NotNull(message = "Number of questions is required")
    @Min(value = 1, message = "Must have at least 1 question")
    private Integer noOfQuestions;
}
