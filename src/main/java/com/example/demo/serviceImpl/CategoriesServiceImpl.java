package com.example.demo.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.constants.Constants;
import com.example.demo.entities.CategoriesEntity;
import com.example.demo.entities.OrganizationEntity;
import com.example.demo.exceptions.BussinessException;
import com.example.demo.repositry.CategoriesRepositry;
import com.example.demo.repositry.OrganizationRepositry;
import com.example.demo.requestDto.CategorieUpdateRequest;
import com.example.demo.requestDto.CategoriesAddRequest;
import com.example.demo.responseDto.CategoriesResponse;
import com.example.demo.service.CategoriesService;
import com.example.demo.utils.ApiResponse;
import com.example.demo.utils.CurrentUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService{

	private final CategoriesRepositry categoriesRepositry;
	private final OrganizationRepositry organizationRepositry;
	private final CurrentUser currentUser;
	
	@Override
	public ApiResponse<Object> addCategories(CategoriesAddRequest request) {
		   Integer orgId = currentUser.orgId();
		  System.out.println(orgId);
		Optional<CategoriesEntity> isExistCategorie=categoriesRepositry.findByName(request.getName());
		OrganizationEntity org=organizationRepositry.findById(orgId).orElseThrow(()->new BussinessException(Constants.ORGANIZATION_NOT_FOUND, Constants.FAILURE_CODE));
		if(isExistCategorie.isPresent()) {
			throw new BussinessException(Constants.CATEGORIES_ALREADY_EXISTS, Constants.FAILURE_CODE);
		}
		CategoriesEntity newCategorie=new CategoriesEntity();
		newCategorie.setActive(true);
		newCategorie.setCreatedDate(LocalDateTime.now());
		newCategorie.setDescription(request.getDescription());
		newCategorie.setName(request.getName());
		newCategorie.setOrganizationCategories(org);
		categoriesRepositry.save(newCategorie);
		
		ApiResponse<Object> res=ApiResponse.success(Constants.CATEGORIES_ADDED_SUCCESS, Constants.SUCCESS_CODE);
		return res;
	}
	
	@Override
	public ApiResponse<List<CategoriesResponse>> getCategoriesByOrg() {
		   Integer orgId = currentUser.orgId();
		OrganizationEntity org=organizationRepositry.findById(orgId).orElseThrow(()->new BussinessException(Constants.ORGANIZATION_NOT_FOUND, Constants.FAILURE_CODE));
		List<CategoriesEntity> categories=org.getCategories();
		List<CategoriesResponse> res=categories.stream().map(this::mapCategoriesResponse).toList();
		ApiResponse<List<CategoriesResponse>> response=ApiResponse.successWithData(Constants.SUCCESS_MESSAGE, res, Constants.SUCCESS_CODE);
		return response;
	}
	
	@Override
	public ApiResponse<Object> updateCategoires(CategorieUpdateRequest request) {
		CategoriesEntity categoire=categoriesRepositry.findById(request.getId()).orElseThrow(()-> new BussinessException(Constants.CATEGORIES_NOT_FOUND, Constants.FAILURE_CODE));
		categoire.setActive(request.getStatus());
		categoire.setUpdatedDate(LocalDateTime.now());
		categoriesRepositry.save(categoire);
		ApiResponse<Object> res=ApiResponse.success(Constants.CATEGORIES_UPDATE_SUCCESS,Constants.SUCCESS_CODE);
		return res;
	}
	
	
	public CategoriesResponse mapCategoriesResponse(CategoriesEntity entity) {
		CategoriesResponse res=new CategoriesResponse();
		res.setActive(entity.getActive());
		res.setCreatedDate(entity.getCreatedDate());
		res.setDescription(entity.getDescription());
		res.setId(entity.getId());
		res.setName(entity.getName());
		res.setUpdatedDate(entity.getUpdatedDate());
		
		return res;
	}
}
