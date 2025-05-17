package com.mcqportal.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerDTO {

    @NotBlank(message = "Answer text must not be blank")
    private String answerText;

    @JsonProperty("isCorrect")
    private boolean isCorrect;
}
