package com.example.demo.service;

import java.util.List;

import com.example.demo.requestDto.CategorieUpdateRequest;
import com.example.demo.requestDto.CategoriesAddRequest;
import com.example.demo.responseDto.CategoriesResponse;
import com.example.demo.utils.ApiResponse;

public interface CategoriesService {
	
	ApiResponse<Object> addCategories(CategoriesAddRequest request);
	
	ApiResponse<List<CategoriesResponse>> getCategoriesByOrg();
	
	ApiResponse<Object> updateCategoires(CategorieUpdateRequest request);
}