package com.example.demo.service;


import java.util.List;

import com.example.demo.requestDto.LoginRequest;
import com.example.demo.requestDto.UpdateUserRequest;
import com.example.demo.requestDto.UserAddRequest;
import com.example.demo.responseDto.LoginResponse;
import com.example.demo.responseDto.UserResponse;
import com.example.demo.utils.ApiResponse;


public interface UsersService{

	ApiResponse<Object> createUser(UserAddRequest request);
	ApiResponse<Object> updateUser(UpdateUserRequest request);
	ApiResponse<List<UserResponse>> getUsersByOrganization(Integer id);
	ApiResponse<Object>  activeOrDeactiveUser(Integer id);
	ApiResponse<List<UserResponse>> getEmployeesUnderManager(Integer id);
	ApiResponse<LoginResponse> login(LoginRequest request);
}
