package com.example.demo.service;

import java.util.List;

import com.example.demo.requestDto.ExpensesAddRequest;
import com.example.demo.requestDto.UpdateExpensesRequest;
import com.example.demo.responseDto.ExpensesResponse;
import com.example.demo.utils.ApiResponse;

public interface ExpensesService {
	
	ApiResponse<Object> createExpenses(ExpensesAddRequest request);
	ApiResponse<Object> updateExpenses(UpdateExpensesRequest request);
	ApiResponse<List<ExpensesResponse>> getExpensesByUserId();
	ApiResponse<List<ExpensesResponse>> getExpensesByOrganization();
}