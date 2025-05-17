package com.mcqportal.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResponse {
    private Long examId;
    private String examTitle;
    private String examCode;
    private LocalDateTime startTime;
    private Integer duration;
    private Integer totalMarks;
    private Integer noOfQuestions;
}
