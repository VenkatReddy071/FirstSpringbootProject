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

import com.example.demo.requestDto.ExpensesAddRequest;
import com.example.demo.requestDto.UpdateExpensesRequest;
import com.example.demo.responseDto.ExpensesResponse;
import com.example.demo.service.ExpensesService;
import com.example.demo.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpensesController {

	private final ExpensesService expensesService;
	

	@PostMapping("/create-expenses")
	private ResponseEntity<ApiResponse<Object>> createExpenses(@Valid @RequestBody ExpensesAddRequest request){
		ApiResponse<Object> res=expensesService.createExpenses(request);
		return new ResponseEntity<>(res,HttpStatus.OK);
		
	}
	
	@PatchMapping("/update-status")
	private ResponseEntity<ApiResponse<Object>> createExpenses(@Valid @RequestBody UpdateExpensesRequest request){
		ApiResponse<Object> res=expensesService.updateExpenses(request);
		return new ResponseEntity<>(res,HttpStatus.OK);
		
	}
	
	@GetMapping("/get-expenses-by-user")
	private ResponseEntity<ApiResponse<List<ExpensesResponse>>> getExpensesByUserId(){
		ApiResponse<List<ExpensesResponse>> res=expensesService.getExpensesByUserId();
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@GetMapping("/get-expenses-by-org")
	private ResponseEntity<ApiResponse<List<ExpensesResponse>>> getExpensesByOrg(){
		ApiResponse<List<ExpensesResponse>> res=expensesService.getExpensesByOrganization();
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
}
