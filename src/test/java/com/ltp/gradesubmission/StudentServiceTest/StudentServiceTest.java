package com.ltp.gradesubmission.StudentServiceTest;

import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.EntityNotFoundException;
import com.ltp.gradesubmission.repository.StudentRepository;
import com.ltp.gradesubmission.service.StudentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {
    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;
    private Student testStudent;
    private Long studentIdTest;

    @Before
    public void init(){
        testStudent = new Student("Roy Cropper", LocalDate.of(2003, 07, 31));
        studentIdTest = 1L;
    }

    @Test
    public void testSuccessfulGetStudent(){
        when(studentRepository.findById(studentIdTest)).thenReturn(Optional.of(testStudent));
        Student student = studentService.getStudent(studentIdTest);

        verify(studentRepository,times(1)).findById(studentIdTest);
        Assertions.assertEquals("Roy Cropper", student.getName());
    }

    @Test
    public void testUnsuccessfulGetStudent(){
        Long studentIdTestFail = 100L;
        when(studentRepository.findById(studentIdTestFail)).thenReturn(Optional.empty());

        var error = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            studentService.getStudent(studentIdTestFail);
        });
        verify(studentRepository, times(1)).findById(studentIdTestFail);
        Assertions.assertEquals(error.getLocalizedMessage(), "The student with id '" + studentIdTestFail + "' does not exist in our records");
    }
}
