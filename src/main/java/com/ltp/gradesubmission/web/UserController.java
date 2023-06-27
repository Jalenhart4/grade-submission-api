package com.ltp.gradesubmission.web;

import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.exception.EntityNotFoundException;
import com.ltp.gradesubmission.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "User Controller", description = "Responsible for registering a user, and finding a specific user in the database")
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {


    UserService userService;

	@Operation(summary = "Get user" , description = "Get user by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful user retrieval" , content = @Content(schema = @Schema(implementation = User.class))),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = EntityNotFoundException.class)))
	})
	@GetMapping("/{id}")
	public ResponseEntity<String> findById(@PathVariable Long id) {
		return new ResponseEntity<>(userService.getUser(id).getUsername(), HttpStatus.OK);
	}

	@Operation(summary = "Register User", description = "Serves as the endpoint for registering a user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created user"),
			@ApiResponse(responseCode = "400", description = "Invalid user registration", content = @Content(schema = @Schema(implementation = DataIntegrityViolationException.class)))
	})
    @PostMapping(value = "/register")
	public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody User user) {
		userService.saveUser(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}



}