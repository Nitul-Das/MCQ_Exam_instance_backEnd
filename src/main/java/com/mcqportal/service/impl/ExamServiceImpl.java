package com.mcqportal.service.impl;

import com.mcqportal.dtos.AnswerDTO;
import com.mcqportal.dtos.CreateExamRequest;
import com.mcqportal.dtos.CreateQuestionRequest;
import com.mcqportal.dtos.ExamResponse;
import com.mcqportal.models.Answer;
import com.mcqportal.models.Exam;
import com.mcqportal.models.Question;
import com.mcqportal.models.User;
import com.mcqportal.repository.AnswerRepository;
import com.mcqportal.repository.ExamRepository;
import com.mcqportal.repository.QuestionRepository;
import com.mcqportal.repository.UserRepository;
import com.mcqportal.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    public ExamResponse createExam(CreateExamRequest request, String teacherEmail) {
        try {
            User teacher = userRepository.findByEmail(teacherEmail)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            String examCode = generateExamCode();

            Exam exam = Exam.builder()
                    .examTitle(request.getExamTitle())
                    .duration(request.getDuration())
                    .totalMarks(request.getTotalMarks())
                    .startTime(request.getStartTime())
                    .noOfQuestions(request.getNoOfQuestions())
                    .examCode(examCode)
                    .creator(teacher)
                    .build();

            Exam saved = examRepository.save(exam);

            return ExamResponse.builder()
                    .examId(saved.getExamId())
                    .examTitle(saved.getExamTitle())
                    .examCode(saved.getExamCode())
                    .startTime(saved.getStartTime())
                    .duration(saved.getDuration())
                    .totalMarks(saved.getTotalMarks())
                    .noOfQuestions(saved.getNoOfQuestions())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create exam. Reason" + e.getMessage());
        }
    }

    private String generateExamCode() {
        return "EXAM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public void addQuestionToExam(Long examId, CreateQuestionRequest request, String teacherEmail) {
        try {
            Exam exam = examRepository.findById(examId)
                    .orElseThrow(() -> new RuntimeException("Exam not found"));

            if (!exam.getCreator().getEmail().equals(teacherEmail)) {
                throw new RuntimeException("You are not authorized to modify this exam");
            }

            long correctCount = request.getAnswers().stream().filter(AnswerDTO::isCorrect).count();
            if (correctCount == 0) {
                throw new RuntimeException("At least one correct answer is required");
            }

            Question question = Question.builder()
                    .question(request.getQuestion())
                    .marks(request.getMarks())
                    .exam(exam)
                    .build();

            Question savedQuestion = questionRepository.save(question);

            List<Answer> answers = request.getAnswers().stream()
                    .map(a -> Answer.builder()
                            .answerText(a.getAnswerText())
                            .isCorrect(a.isCorrect())
                            .question(savedQuestion)
                            .build())
                    .toList();
            answerRepository.saveAll(answers);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add question: " + e.getMessage());
        }
    }
}
