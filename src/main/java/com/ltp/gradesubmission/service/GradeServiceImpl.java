package com.ltp.gradesubmission.service;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.GradeNotFoundException;
import com.ltp.gradesubmission.exception.StudentNotEnrolledException;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {
    
    private GradeRepository gradeRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    
    @Override
    public Grade getGrade(Long studentId, Long courseId) {
         Optional<Grade> grade = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
         return unwrapGrade(grade, studentId, courseId);
    }

    @Override
    public Grade saveGrade(Grade grade, Long studentId, Long courseId) {
        Student student = StudentServiceImpl.unwrapStudent(studentRepository.findById(studentId), studentId);
        Course course = CourseServiceImpl.unwrapCourse(courseRepository.findById(courseId), courseId);
        if (!student.getCourses().contains(course)) {
            throw new StudentNotEnrolledException(studentId, courseId);
        }
        grade.setStudent(student);
        grade.setCourse(course);
        return gradeRepository.save(grade);
    }

    @Override
    public Grade updateGrade(Grade newGrade, Long studentId, Long courseId) {
        Optional<Grade> currentGrade = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
        Grade unwrappedcurrentGrade = unwrapGrade(currentGrade, studentId, courseId);
        unwrappedcurrentGrade.setScore(newGrade.getScore());
        return gradeRepository.save(unwrappedcurrentGrade);
    }

    @Override
    public void deleteGrade(Grade gradeToDelete, Long studentId, Long courseId) {
        Long gradeId = gradeToDelete.getId();
        if(gradeRepository.existsById(gradeId)){
            gradeRepository.deleteByIdAndStudentIdAndCourseId(gradeId, studentId, courseId);
        }
        else throw new GradeNotFoundException(gradeId,studentId,courseId);
    }

    @Override
    public List<Grade> getStudentGrades(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    @Override
    public List<Grade> getCourseGrades(Long courseId) {
        return gradeRepository.findByCourseId(courseId);
    }

    @Override
    public List<Grade> getAllGrades() {
        return (List<Grade>) gradeRepository.findAll();
    }

    static Grade unwrapGrade(Optional<Grade> entity, Long studentId, Long courseId) {
        if (entity.isPresent()) return entity.get();
        else throw new GradeNotFoundException(studentId, courseId);
    }

}
