package com.ltp.gradesubmission.service;

import com.ltp.gradesubmission.entity.Grade;

import java.util.List;

public interface GradeService {
    Grade getGrade(Long studentId, Long courseId);
    Grade saveGrade(Grade grade, Long studentId, Long courseId);
    Grade updateGrade(Grade newGrade, Long studentId, Long courseId);
    void deleteGrade(Grade gradeToDelete, Long studentId, Long courseId);
    List<Grade> getStudentGrades(Long studentId);
    List<Grade> getCourseGrades(Long courseId);
    List<Grade> getAllGrades();
}
