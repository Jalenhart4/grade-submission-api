package com.ltp.gradesubmission.GradeServiceTest;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.EntityNotFoundException;
import com.ltp.gradesubmission.exception.GradeNotFoundException;
import com.ltp.gradesubmission.exception.StudentNotEnrolledException;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import com.ltp.gradesubmission.service.CourseServiceImpl;
import com.ltp.gradesubmission.service.GradeServiceImpl;
import com.ltp.gradesubmission.service.StudentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {
    @InjectMocks
    private GradeServiceImpl gradeService;

    @Mock
    private CourseServiceImpl courseService;

    @Mock
    private StudentServiceImpl studentService;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;
    private Long studentId;
    private Long courseId;
    private Grade testGrade;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        studentId = 1L;
        courseId = 1L;
        testGrade = new Grade(1L, "A-", new Student("Roy Cropper", LocalDate.of(2003, 07, 31)), new Course("Gym", "GYM101", "In this course we teach physical education."));
    }
    @Test
    public void testSuccessfulGetGrade() {

        when(gradeRepository.findByStudentIdAndCourseId(studentId,courseId)).thenReturn(Optional.of(testGrade));

        Grade resultGrade = gradeService.getGrade(studentId,courseId);

        verify(gradeRepository).findByStudentIdAndCourseId(studentId,courseId);
        Assertions.assertEquals("A-", resultGrade.getScore());
    }

    @Test()
    public void testUnsuccessfulGetGrade() throws GradeNotFoundException {

        Long studentIdTest  = 100L;
        Long courseIdTest = 100L;
        Optional<Grade> testGrade = Optional.empty();

        when(gradeRepository.findByStudentIdAndCourseId(studentIdTest,courseIdTest)).thenReturn(testGrade);
        var error = Assertions.assertThrows(GradeNotFoundException.class, ()-> {
            gradeService.getGrade(studentIdTest,courseIdTest);
        });
        verify(gradeRepository, times(1)).findByStudentIdAndCourseId(studentIdTest,courseIdTest);
        Assertions.assertEquals(error.getLocalizedMessage(), "The grade with student id: '" + studentIdTest + "' and course id: '" + courseIdTest + "' does not exist in our records");

    }

    @Test
    public void testSuccessfulSaveGrade() {

        Course course = new Course("Gym", "GYM101", "In this course we teach physical education.");
        Set<Course> courseSet = new HashSet<Course>();
        List<Grade> grades = Arrays.asList(new Grade(1L, "A+", mock(Student.class), mock(Course.class)),new Grade(2L, "A+", mock(Student.class), mock(Course.class)));
        courseSet.add(course);
        Student student = new Student(studentId,"Roy Cropper",LocalDate.of(2003, 07, 31),grades,courseSet);
        //Student + grades + courses
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        //find course
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        //find course in student courses
        Assertions.assertDoesNotThrow(()-> {
            gradeService.saveGrade(mock(),studentId,courseId);
        });
    }
    @Test
    public void testUnsuccessfulSaveGrade() throws EntityNotFoundException, GradeNotFoundException {

        Course course = new Course("Gym", "GYM101", "In this course we teach physical education.");
        Set<Course> courseSet = new HashSet<Course>();
        List<Grade> grades = Arrays.asList(new Grade(1L, "A+", mock(Student.class), mock(Course.class)),new Grade(2L, "A+", mock(Student.class), mock(Course.class)));

        //Do not add course to student ->
        //courseSet.add(course);
        // -> throws student not enrolled exception after !student.getCourses().contains(course) evaluates to true.

        Student student = new Student(studentId,"Roy Cropper",LocalDate.of(2003, 07, 31),grades,courseSet);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Assertions.assertThrows(StudentNotEnrolledException.class,()-> {
            gradeService.saveGrade(mock(),studentId,courseId);
        });


    }
    @Test
    public void testSuccessfulUpdateGrade(){
        Optional<Grade> testGradeToUpdate = Optional.of(testGrade);
        testGradeToUpdate.get().setScore("A+"); //Copy testGrade, change score from default so it can be updated
        when(gradeRepository.findByStudentIdAndCourseId(studentId, courseId)).thenReturn(testGradeToUpdate);
        gradeService.updateGrade(testGrade,studentId,courseId);
        Assertions.assertEquals(testGrade.getScore(),testGradeToUpdate.get().getScore());
    }
    @Test
    public void testUnsuccessfulUpdateGrade(){

        when(gradeRepository.findByStudentIdAndCourseId(studentId, courseId)).thenReturn(Optional.empty());
        var error = Assertions.assertThrows(GradeNotFoundException.class, ()-> {
            gradeService.updateGrade(testGrade,studentId,courseId);
        });

        Assertions.assertEquals(error.getLocalizedMessage(), "The grade with student id: '" + studentId + "' and course id: '" + courseId + "' does not exist in our records");
    }
    @Test
    public void testSuccessfulDeleteGrade(){
        Long gradeId = 1L;
        when(gradeRepository.existsById(gradeId)).thenReturn(true);
        gradeService.deleteGrade(testGrade,studentId,courseId);

        verify(gradeRepository, times(1)).existsById(gradeId);
        verify(gradeRepository, times(1)).deleteByIdAndStudentIdAndCourseId(gradeId,studentId,courseId);
    }

    @Test
    public void testUnsuccessfulDeleteGrade(){

        Grade gradeToDelete = mock();
        Long gradeId = 1L;
        when(gradeToDelete.getId()).thenReturn(gradeId);
        when(gradeRepository.existsById(gradeId)).thenReturn(false);

        var error = Assertions.assertThrows(GradeNotFoundException.class, ()-> {
            gradeService.deleteGrade(gradeToDelete,studentId,courseId);
        });

        verify(gradeRepository, times(1)).existsById(gradeId);
        verify(gradeRepository, times(0)).deleteByIdAndStudentIdAndCourseId(gradeId,studentId,courseId);
        Assertions.assertEquals(error.getLocalizedMessage(), "The grade with id: '" + gradeId + "' with student id: '" + studentId + "' and course id: '" + courseId + "' does not exist in our records");
    }

    @Test
    public void testGetAllGrades(){

        when(gradeRepository.findAll()).thenReturn(Arrays.asList(
                new Grade(1L,"A+", mock(Student.class), mock(Course.class)),
                new Grade(2L,"C", mock(Student.class), mock(Course.class)),
                new Grade(3L,"A", mock(Student.class), mock(Course.class))
        ));

        List<Grade> result = gradeService.getAllGrades();

        Assertions.assertEquals(3, result.size());

    }
}
