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

import com.example.demo.requestDto.OrganizationRegiserRequest;
import com.example.demo.requestDto.UpdateOrganizationRequest;
import com.example.demo.responseDto.OrganizationResponse;
import com.example.demo.service.OrganizationService;
import com.example.demo.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organization")
public class OrgController {

	private final OrganizationService organizationService;



	@PostMapping("/register")
	private ResponseEntity<ApiResponse<Object>> createOrganization(@Valid @RequestBody OrganizationRegiserRequest request){
		ApiResponse<Object> res= organizationService.createOrganization(request);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	@PatchMapping("/update-organization-status")
	private ResponseEntity<ApiResponse<Object>> createOrganization(@Valid @RequestBody UpdateOrganizationRequest request){
		ApiResponse<Object> res= organizationService.updateOrganizationInfo(request);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	@GetMapping("/get-all-organizations")
	private ResponseEntity<ApiResponse<List<OrganizationResponse>>> getAllOrganizations(){
		ApiResponse<List<OrganizationResponse>> res=organizationService.getAllOrganizations();
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	@GetMapping("/get-organization-by-id/{id}")
	private ResponseEntity<ApiResponse<OrganizationResponse>> getOrganizationById(@PathVariable("id") Integer id){
		ApiResponse<OrganizationResponse> res=organizationService.getOrganizationById(id);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
		
}
