package com.example.demo.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.constants.Constants;

import com.example.demo.entities.CategoriesEntity;
import com.example.demo.entities.ExpensesEntity;
import com.example.demo.entities.OrganizationEntity;
import com.example.demo.entities.UsersEntity;
import com.example.demo.exceptions.BussinessException;
import com.example.demo.repositry.CategoriesRepositry;
import com.example.demo.repositry.ExpensesRepositry;
import com.example.demo.repositry.OrganizationRepositry;
import com.example.demo.repositry.UserRepositry;
import com.example.demo.requestDto.ExpensesAddRequest;
import com.example.demo.requestDto.UpdateExpensesRequest;
import com.example.demo.responseDto.ExpensesResponse;
import com.example.demo.service.ExpensesService;
import com.example.demo.utils.ApiResponse;
import com.example.demo.utils.CurrentUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpensesServiceImpl implements ExpensesService{

	private final ExpensesRepositry expensesRepositry;
	private final OrganizationRepositry organizationRepositry;
	private final UserRepositry userRepositry;
	private final CategoriesRepositry categoriesRepositry;
	private final CurrentUser currentUser;
	
	@Override
		public ApiResponse<Object> createExpenses(ExpensesAddRequest request) {
		  Integer orgId = currentUser.orgId();
		   Integer userId=currentUser.userId();
			Optional<ExpensesEntity> isAlreadyExist=expensesRepositry.findByCategorie_IdAndAmountAndUsers_Id(request.getCategoryId(), request.getAmount(),userId);
			if(isAlreadyExist.isPresent()) {
				throw new BussinessException(Constants.EXPENSE_ALREADY_EXISTS, Constants.FAILURE_CODE);
			}
			 
			OrganizationEntity org=organizationRepositry.findById(orgId).orElseThrow(()->new BussinessException(Constants.ORGANIZATION_NOT_FOUND, Constants.FAILURE_CODE));
			UsersEntity user=userRepositry.findById(userId).orElseThrow(()->new BussinessException(Constants.USER_NOT_FOUND, Constants.FAILURE_CODE));
			CategoriesEntity categoire=categoriesRepositry.findById(request.getCategoryId()).orElseThrow(()-> new BussinessException(Constants.CATEGORIES_NOT_FOUND, Constants.FAILURE_CODE));
			ExpensesEntity expenses=new ExpensesEntity();
			expenses.setAmount(request.getAmount());
			expenses.setCategorie(categoire);
			expenses.setCreatedDate(LocalDateTime.now());
			expenses.setDescription(request.getDescription());
			expenses.setExpensesDate(request.getExpensesDate());
			expenses.setStatus(request.getStatus());
			expenses.setSubmittedDate(LocalDateTime.now());
			expenses.setOrganizationExpenses(org);
			expenses.setUsers(user);
			
			expensesRepositry.save(expenses);
			ApiResponse<Object> res=ApiResponse.success(Constants.EXPENSE_ADDED_SUCCESS, Constants.SUCCESS_CODE);
			return res;
		}
	
	@Override
	public ApiResponse<List<ExpensesResponse>> getExpensesByOrganization() {
		   Integer orgId = currentUser.orgId();
		OrganizationEntity org=organizationRepositry.findById(orgId).orElseThrow(()->new BussinessException(Constants.ORGANIZATION_NOT_FOUND, Constants.FAILURE_CODE));
		List<ExpensesResponse> expenses=org.getExpenses().stream().map(this::mapResponse).toList();
		ApiResponse<List<ExpensesResponse>> res=ApiResponse.successWithData(Constants.SUCCESS_MESSAGE, expenses,Constants.SUCCESS_CODE);
		return res;
	}
	
	@Override
	public ApiResponse<List<ExpensesResponse>> getExpensesByUserId() {
		Integer userId=currentUser.userId();
		UsersEntity user=userRepositry.findById(userId).orElseThrow(()->new BussinessException(Constants.USER_NOT_FOUND, Constants.FAILURE_CODE));
		List<ExpensesResponse> expenses=user.getExpenses().stream().map(this::mapResponse).toList();
		ApiResponse<List<ExpensesResponse>> res=ApiResponse.successWithData(Constants.SUCCESS_MESSAGE, expenses,Constants.SUCCESS_CODE);
		return res;
	}
	
	@Override
	public ApiResponse<Object> updateExpenses(UpdateExpensesRequest request) {
		ExpensesEntity expenses=expensesRepositry.findById(request.getId()).orElseThrow(()-> new BussinessException(Constants.EXPENSE_NOT_FOUND, Constants.FAILURE_CODE));
		expenses.setStatus(request.getStatus());
		expenses.setStatusUpdatedDate(LocalDateTime.now());
		expenses.setUpdatedDate(LocalDateTime.now());
		if(request.getRejectReason()!=null) {
			expenses.setRejectReason(request.getRejectReason());
		}
		expensesRepositry.save(expenses);
		
		ApiResponse<Object> response=ApiResponse.success(Constants.EXPENSE_UPDATED_SUCCESS, Constants.SUCCESS_CODE);
		return response;
	}
	
	public ExpensesResponse mapResponse(ExpensesEntity entity) {
	    ExpensesResponse res = new ExpensesResponse();

	    res.setId(entity.getId());
	    res.setAmount(entity.getAmount());
	    res.setDescription(entity.getDescription());

	    res.setExpensesDate(entity.getExpensesDate());
	    res.setCreatedDate(entity.getCreatedDate());
	    res.setUpdatedDate(entity.getUpdatedDate());
	    res.setSubmittedDate(entity.getSubmittedDate());
	    res.setStatusUpdatedDate(entity.getStatusUpdatedDate());

	    res.setRejectReason(entity.getRejectReason());
	    res.setStatus(entity.getStatus());

	    if (entity.getCategorie() != null) {
	        res.setCategoryId(entity.getCategorie().getId());
	        res.setCategoryName(entity.getCategorie().getName());
	    }

	    if (entity.getUsers() != null) {
	        res.setUserId(entity.getUsers().getId());
	        res.setUserEmail(entity.getUsers().getEmail());
	        res.setUserName(entity.getUsers().getFirstName());
	    }

	    if (entity.getOrganizationExpenses() != null) {
	        res.setOrganizationName(
	            entity.getOrganizationExpenses().getOrgName()
	        );
	    }

	    return res;
	}
}

