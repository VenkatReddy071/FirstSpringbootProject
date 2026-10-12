package com.example.demo.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.requestDto.LoginRequest;
import com.example.demo.requestDto.UpdateUserRequest;
import com.example.demo.requestDto.UserAddRequest;
import com.example.demo.responseDto.LoginResponse;
import com.example.demo.responseDto.UserResponse;
import com.example.demo.service.UsersService;
import com.example.demo.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UsersService usersService;
	
	
	
	@PostMapping("/create-user")
	private ResponseEntity<ApiResponse<Object>> createUser(@Valid @RequestBody UserAddRequest request ){
		ApiResponse<Object> res=usersService.createUser(request);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@PutMapping("/update-user")
	private ResponseEntity<ApiResponse<Object>> updateUser(@Valid @RequestBody UpdateUserRequest request ){
		ApiResponse<Object> res=usersService.updateUser(request);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@GetMapping("/get-users-by-org/{id}")
	private ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsersByOrg(@PathVariable("id") Integer id){
		ApiResponse<List<UserResponse>> res=usersService.getUsersByOrganization(id);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@PatchMapping("/update-status-by-id/{id}")
	private ResponseEntity<ApiResponse<Object>> updateUserStatus(@PathVariable("id") Integer id ){
		ApiResponse<Object> res=usersService.activeOrDeactiveUser(id);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@GetMapping("/get-users-under-manager/{id}")
	private ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsersByManager(@PathVariable("id") Integer id){
		ApiResponse<List<UserResponse>> res=usersService.getEmployeesUnderManager(id);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	private ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request){
		ApiResponse<LoginResponse> res=usersService.login(request);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
 	
}
