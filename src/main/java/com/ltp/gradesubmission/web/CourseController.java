package com.ltp.gradesubmission.web;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.EntityNotFoundException;
import com.ltp.gradesubmission.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Tag(name = "Course Controller", description = "Responsible for creating, retrieving, and deleting courses. Includes endpoint for enrolling students to courses.")
@AllArgsConstructor
@RestController
@RequestMapping("/course")
public class CourseController {

    CourseService courseService;

    @Operation(summary = "Get course by ID", description = "Returns a course given an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Course doesn't exist", content = @Content(schema = @Schema(implementation = EntityNotFoundException.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of course", content = @Content(schema = @Schema(implementation = Course.class)))
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.getCourse(id), HttpStatus.OK);
    }

    @Operation(summary = "Create course", description = "Creates a new course")
    @ApiResponse(responseCode = "201", description = "Successful creation of course")
    @PostMapping
    public ResponseEntity<Course> saveCourse(@Valid @RequestBody Course course) {
        courseService.saveCourse(course);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Delete course by ID", description = "Deletes course based on ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Course doesn't exist", content = @Content(schema = @Schema(implementation = EmptyResultDataAccessException.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of course", content = @Content(schema = @Schema(implementation = Course.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all courses", description = "retrieves all courses")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all courses", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Course.class))))
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> getCourses() {
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }

    @Operation(summary = "Enroll a student to a course", description = "Adds an existing student to an existing course given the course ID and student ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student successfully enrolled in course"),
            @ApiResponse(responseCode = "400", description = "Student and/or course not found", content = @Content(schema = @Schema(implementation = EntityNotFoundException.class)))
    })
    @PutMapping(value = "/{courseId}/student/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> enrollStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.addStudentToCourse(studentId,courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get students enrolled in a course", description = "Retrieves a set of students enrolled in one course ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" , description = "Successfully retrieved all students enrolled in the course", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class)))),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content(schema = @Schema(implementation = EntityNotFoundException.class)))
    })
    @GetMapping(value = "/{id}/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Student>> getEnrolledStudents(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.getEnrolledStudents(id), HttpStatus.OK);
    }

}