package com.example.demo.service;

import java.util.List;

import com.example.demo.requestDto.RoleAddRequest;
import com.example.demo.responseDto.RoleResponse;
import com.example.demo.utils.ApiResponse;

public interface RoleService {
	
	ApiResponse<Object> createRole(RoleAddRequest request);
	ApiResponse<List<RoleResponse>> getAllRoles();
}