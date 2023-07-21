package com.ltp.gradesubmission.CourseServiceTest;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.EntityNotFoundException;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import com.ltp.gradesubmission.service.CourseServiceImpl;
import com.ltp.gradesubmission.service.StudentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {
    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentServiceImpl studentService;
    @Mock
    private StudentRepository studentRepository;
    private Course testcourse;
    private Long courseIdTest;

    @Before
    public void init(){
        testcourse = new Course("Gym", "GYM101", "In this course we teach about Physical Education.");
        courseIdTest = 1L;
    }

    @Test
    public void testSuccessfulGetCourse(){
        when(courseRepository.findById(courseIdTest)).thenReturn(Optional.of(testcourse));
        Course course = courseService.getCourse(courseIdTest);

        verify(courseRepository,times(1)).findById(courseIdTest);
        Assertions.assertEquals("GYM101", course.getCode());
    }

    @Test
    public void testUnsuccessfulGetCourse(){
        Long courseIdTestFail = 100L;
        when(courseRepository.findById(courseIdTestFail)).thenReturn(Optional.empty());

        var error = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            courseService.getCourse(courseIdTestFail);
        });
        verify(courseRepository, times(1)).findById(courseIdTestFail);
        Assertions.assertEquals(error.getLocalizedMessage(), "The course with id '" + courseIdTestFail + "' does not exist in our records");
    }

    // Add student to course tests !!!

    @Test
    public void testSuccessfulAddStudentToCourse(){
        Long studentIdTest = 1L;
        Set<Student> students = new HashSet<Student>();
        Student testStudent = new Student("Roy Cropper", LocalDate.of(2003, 07, 31));
        Course testCourse = new Course(1L,"Gym", "GYM101", "In this course we teach physical education.", mock(), students);
        when(courseRepository.findById(courseIdTest)).thenReturn(Optional.of(testCourse));
        when(studentRepository.findById(studentIdTest)).thenReturn(Optional.of(testStudent));
        testCourse.getStudents().add(testStudent);
        when(courseRepository.save(testCourse)).thenReturn(testCourse);
        Course updatedTestCourse = courseService.addStudentToCourse(studentIdTest, courseIdTest);

        Assertions.assertEquals(updatedTestCourse.getStudents().stream().count(), 1L);

    }

}
