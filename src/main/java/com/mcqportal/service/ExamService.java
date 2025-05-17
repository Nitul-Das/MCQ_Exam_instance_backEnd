package com.mcqportal.service;

import com.mcqportal.dtos.CreateExamRequest;
import com.mcqportal.dtos.CreateQuestionRequest;
import com.mcqportal.dtos.ExamResponse;

public interface ExamService {
    ExamResponse createExam(CreateExamRequest request, String teacherEmail);

    void addQuestionToExam(Long examId, CreateQuestionRequest request, String teacherEmail);
}
