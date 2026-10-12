package com.example.demo.service;

import java.util.List;




import com.example.demo.requestDto.OrganizationRegiserRequest;
import com.example.demo.requestDto.UpdateOrganizationRequest;
import com.example.demo.responseDto.OrganizationResponse;
import com.example.demo.utils.ApiResponse;


public interface OrganizationService{

	ApiResponse<Object> createOrganization(OrganizationRegiserRequest request);
	ApiResponse<Object> updateOrganizationInfo(UpdateOrganizationRequest request);
	ApiResponse<List<OrganizationResponse>> getAllOrganizations();
	ApiResponse<OrganizationResponse> getOrganizationById(Integer id);
	
}
