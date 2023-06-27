package com.ltp.gradesubmission.web;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.EntityNotFoundException;
import com.ltp.gradesubmission.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Tag(name = "Student Controller", description = "Endpoints for creating, retrieving, and deleting students. Includes endpoint for retrieving courses enrolled in by student.")
@AllArgsConstructor
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Operation(summary = "Get student", description = "Get student by ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of student", content = @Content(schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content(schema = @Schema(implementation = EntityNotFoundException.class)))
    })
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.getStudent(id), HttpStatus.OK);
    }


    @Operation(summary = "Create student", description = "Create student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created student", content = @Content(schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "400", description = "Unsuccessfully created student")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student) {
        return new ResponseEntity<>(studentService.saveStudent(student), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete student", description = "Delete student by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful deletion of student"),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content(schema = @Schema(implementation = EmptyResultDataAccessException.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get students", description = "Get a list of all students")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of students", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class))))
    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getStudents() {
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    @Operation(summary = "Get enrolled courses", description = "Get set of courses enrolled in for student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of courses enrolled in for student", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Course.class)))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content(schema = @Schema(implementation = EntityNotFoundException.class)))
    })
    @GetMapping(value = "/{id}/courses",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Course>> getEnrolledCourses(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.getEnrolledCourses(id), HttpStatus.OK);
    }

}
