package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.responseDto.OrgStatusResponse;
import com.example.demo.service.OrgStatusService;
import com.example.demo.service.OrganizationService;
import com.example.demo.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/org/status")
public class OrgStatusController {

	private final OrgStatusService orgStatusService;
	
	@GetMapping("/by-org/{id}")
	private ResponseEntity<ApiResponse<List<OrgStatusResponse>>> fingStatusByOrgId(@PathVariable("id") Integer id){
		ApiResponse<List<OrgStatusResponse>> res=orgStatusService.getOrgStatus(id);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
}
