package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.requestDto.RoleAddRequest;
import com.example.demo.responseDto.RoleResponse;
import com.example.demo.service.RoleService;
import com.example.demo.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

	private final RoleService roleService;
	
	@PostMapping("/create-role")
	public ResponseEntity<ApiResponse<Object>> createRole(@Valid @RequestBody RoleAddRequest request){
		
		ApiResponse<Object> role=roleService.createRole(request);
		return new ResponseEntity<>(role,HttpStatus.OK);
	}
	
	@GetMapping("/get-all-roles")
     public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles(){
		
		ApiResponse<List<RoleResponse>> role=roleService.getAllRoles();
		return new ResponseEntity<>(role,HttpStatus.OK);
	}
}
