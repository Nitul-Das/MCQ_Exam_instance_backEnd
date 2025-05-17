package com.mcqportal.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateQuestionRequest {

    @NotBlank(message = "Question text must not be blank")
    private String question;

    @NotNull(message = "Marks must be provided")
    @Min(value = 1, message = "Marks must be at least 1")
    private Integer marks;

    @NotNull(message = "Answers list must not be null")
    @Size(min = 2, message = "At least 2 answer options are required")
    private List<@Valid AnswerDTO> answers;
}
