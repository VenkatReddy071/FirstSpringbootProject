package com.example.demo.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;import org.springframework.data.core.ReactiveWrappers.ReactiveLibrary;
import org.springframework.stereotype.Service;

import com.example.demo.constants.Constants;
import com.example.demo.constants.OrgStatusEnum;
import com.example.demo.entities.OrgStatusEntity;
import com.example.demo.entities.OrganizationEntity;
import com.example.demo.exceptions.BussinessException;
import com.example.demo.mapper.CommonMethodsMapper;
import com.example.demo.repositry.OrgStatusRepositry;
import com.example.demo.repositry.OrganizationRepositry;
import com.example.demo.requestDto.OrgStatusRequest;
import com.example.demo.responseDto.OrgStatusResponse;
import com.example.demo.service.OrgStatusService;
import com.example.demo.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrgStatusServiceImpl implements OrgStatusService{

	private final OrgStatusRepositry statusRepositry;
	private final OrganizationRepositry organizationRepositry;
	private final CommonMethodsMapper mapper;
	@Override
	public ApiResponse<Object> createStatus(OrgStatusRequest request) {
		OrganizationEntity org=organizationRepositry.findById(request.getOrganizationId()).orElseThrow(()->new BussinessException(Constants.ORGANIZATION_NOT_FOUND, Constants.FAILURE_CODE));
		OrgStatusEntity newStatus=new OrgStatusEntity();
		newStatus.setCreatedDate(LocalDateTime.now());
		newStatus.setStatus(request.getStatus());
		newStatus.setOrganizationStatus(org);
		statusRepositry.save(newStatus);
		ApiResponse<Object> res=ApiResponse.success(Constants.SUCCESS_MESSAGE, Constants.SUCCESS_CODE);
		return res;
	}
	@Override
	public ApiResponse<List<OrgStatusResponse>> getOrgStatus(Integer id) {
		List<OrgStatusEntity> status=statusRepositry.findByOrganizationStatus_id(id);
		List<OrgStatusResponse> res=status.stream().map(this.mapper::mapOrgStatusResponse).toList();
		ApiResponse<List<OrgStatusResponse>> response=ApiResponse.successWithData(Constants.SUCCESS_MESSAGE, res, Constants.SUCCESS_CODE);
		return response;
	}
	
}
