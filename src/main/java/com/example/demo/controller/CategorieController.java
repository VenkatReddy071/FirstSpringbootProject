package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.requestDto.CategorieUpdateRequest;
import com.example.demo.requestDto.CategoriesAddRequest;
import com.example.demo.responseDto.CategoriesResponse;
import com.example.demo.secutiry.JWTService;
import com.example.demo.service.CategoriesService;
import com.example.demo.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategorieController {

	private final CategoriesService categoriesService;

	@PostMapping("/add")
	private ResponseEntity<ApiResponse<Object>> addCategories(@Valid @RequestBody CategoriesAddRequest request){
		ApiResponse<Object> res=categoriesService.addCategories(request);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@GetMapping("/get-by-org-id")
	private ResponseEntity<ApiResponse<List<CategoriesResponse>>> getCategoriesByOrg(){
		ApiResponse<List<CategoriesResponse>> res=categoriesService.getCategoriesByOrg();
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@PatchMapping("/update-status")
	private ResponseEntity<ApiResponse<Object>> updateCategoires(@Valid @RequestBody CategorieUpdateRequest request){
		ApiResponse<Object> res=categoriesService.updateCategoires(request);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
}
