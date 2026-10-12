package com.example.demo.service;

import java.util.List;

import com.example.demo.requestDto.OrgStatusRequest;
import com.example.demo.responseDto.OrgStatusResponse;
import com.example.demo.utils.ApiResponse;

public interface OrgStatusService {

	ApiResponse<Object> createStatus(OrgStatusRequest request);
	ApiResponse<List<OrgStatusResponse>> getOrgStatus(Integer id);
}
