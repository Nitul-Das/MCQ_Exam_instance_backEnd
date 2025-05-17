package com.mcqportal.controllers;

import com.mcqportal.dtos.CreateExamRequest;
import com.mcqportal.dtos.CreateQuestionRequest;
import com.mcqportal.dtos.ExamResponse;
import com.mcqportal.service.ExamService;
import com.mcqportal.service.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/exams")
    public ResponseEntity<ExamResponse> createExam(@Valid @RequestBody CreateExamRequest request, @AuthenticationPrincipal UserDetailsImpl currentUser) {
        try {
            ExamResponse response = examService.createExam(request, currentUser.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ExamResponse.builder()
                            .examTitle("Error: " + e.getMessage())
                            .build()
            );
        }
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/{examId}/questions")
    public ResponseEntity<?> addQuestionToExam(@PathVariable Long examId, @Valid @RequestBody CreateQuestionRequest request, @AuthenticationPrincipal UserDetailsImpl currentUser) {
        try {
            examService.addQuestionToExam(examId, request, currentUser.getEmail());
            return ResponseEntity.ok("Question Added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
