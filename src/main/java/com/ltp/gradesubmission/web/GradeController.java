package com.ltp.gradesubmission.web;

import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.exception.GradeNotFoundException;
import com.ltp.gradesubmission.service.GradeService;
import com.ltp.gradesubmission.validation.ScoreValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Grade Controller", description = "Responsible for managing students grades in courses.")
@AllArgsConstructor
@RestController
@RequestMapping("/grade")
public class GradeController {
    
    GradeService gradeService;

    @Operation(summary = "Get grade", description = "Retrieves a students grade based on ID and the course ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved students grade from the course.", content = @Content(schema = @Schema(implementation=Grade.class))),
            @ApiResponse(responseCode = "404", description = "Grade not found", content = @Content(schema = @Schema(implementation = GradeNotFoundException.class)))
    })
    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<Grade> getGrade(@PathVariable Long studentId, @PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.getGrade(studentId, courseId), HttpStatus.OK);
    }

    @Operation(summary = "Create grade", description = "Creates a grade in a course for a student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Grade already exists", content = @Content(schema = @Schema(implementation = DataIntegrityViolationException.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Score", content = @Content(schema = @Schema(implementation = ScoreValidator.class))),
            @ApiResponse(responseCode = "200", description = "Grade successfully created", content = @Content(schema = @Schema(implementation = Grade.class)))
    })
    @PostMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<Grade> saveGrade(@Valid @RequestBody Grade grade, @PathVariable Long studentId, @PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.saveGrade(grade, studentId, courseId), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Grade", description = "Update an existing grade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grade successfully updated", content = @Content(schema = @Schema(implementation = Grade.class))),
            @ApiResponse(responseCode = "404", description = "Grade not found", content = @Content(schema = @Schema(implementation = GradeNotFoundException.class)))
    })
    @PutMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<Grade> updateGrade(@Valid @RequestBody Grade grade, @PathVariable Long studentId, @PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.updateGrade(grade, studentId, courseId), HttpStatus.OK);
    }

    @Operation(summary = "Delete grade", description = "Delete a grade for a student in a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted course"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ScoreValidator.class))),
            @ApiResponse(responseCode = "404", description = "Grade not found", content = @Content(schema = @Schema(implementation = GradeNotFoundException.class)))
    })
    @DeleteMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<HttpStatus> deleteGrade(@Valid @RequestBody Grade grade, @PathVariable Long studentId, @PathVariable Long courseId) {
        gradeService.deleteGrade(grade, studentId, courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get student grades", description = "Get all grades for a student")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all grades of the student", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Grade.class))))
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Grade>> getStudentGrades(@PathVariable Long studentId) {
        return new ResponseEntity<>(gradeService.getStudentGrades(studentId), HttpStatus.OK);
    }

    @Operation(summary = "Get course grades", description = "Get all grades for a course")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all course grades", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Grade.class))))
    @GetMapping(value = "/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Grade>> getCourseGrades(@PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.getCourseGrades(courseId), HttpStatus.OK);
    }

    @Operation(summary = "Get all grades", description = "Retrieve all grades")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all grades", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Grade.class))))
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Grade>> getGrades() {
        return new ResponseEntity<>(gradeService.getAllGrades(), HttpStatus.OK);
    }

}
