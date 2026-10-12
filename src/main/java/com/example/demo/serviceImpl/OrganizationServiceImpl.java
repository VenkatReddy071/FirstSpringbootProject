package com.example.demo.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.example.demo.constants.Constants;
import com.example.demo.constants.OrgStatusEnum;
import com.example.demo.constants.UserRoleEnum;

import com.example.demo.entities.OrganizationEntity;
import com.example.demo.entities.RoleEntity;
import com.example.demo.exceptions.BussinessException;
import com.example.demo.mapper.CommonMethodsMapper;
import com.example.demo.repositry.OrganizationRepositry;
import com.example.demo.repositry.RoleRepositry;
import com.example.demo.requestDto.OrgStatusRequest;
import com.example.demo.requestDto.OrganizationRegiserRequest;
import com.example.demo.requestDto.UpdateOrganizationRequest;
import com.example.demo.requestDto.UserAddRequest;
import com.example.demo.responseDto.OrgStatusResponse;
import com.example.demo.responseDto.OrganizationResponse;
import com.example.demo.service.OrgStatusService;
import com.example.demo.service.OrganizationService;
import com.example.demo.service.UsersService;
import com.example.demo.utils.ApiResponse;
import com.example.demo.utils.CurrentUser;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService{

	private final OrganizationRepositry organizationRepositry;
	private final UsersService userService;
	private final RoleRepositry roleRepositry;
	private final OrgStatusService orgStatusService;
	private final CommonMethodsMapper mapper;
	private final CurrentUser currentUser;
	
	@Override
	@Transactional
	public ApiResponse<Object> createOrganization(OrganizationRegiserRequest request) {
		
		try {
		Optional<OrganizationEntity> existOrganization=organizationRepositry.findByEmail(request.getEmail());
		Optional<RoleEntity> findOrgHeadRole=roleRepositry.findByRoleName(UserRoleEnum.ORG_ADMIN);
		if(findOrgHeadRole.isEmpty()) {
			throw new BussinessException(Constants.ROLE_NOT_FOUND, Constants.FAILURE_CODE);
		}
		if(existOrganization.isPresent()) {
			throw new BussinessException(Constants.ORGANIZATION_ALREADY_EXISTS, Constants.FAILURE_CODE);
		}
		OrganizationEntity newOrganization =new OrganizationEntity();
		newOrganization.setOrgName(request.getOrganizationName());
		newOrganization.setEmail(request.getEmail());
		newOrganization.setCreatedDate(LocalDateTime.now());
		newOrganization.setSlug(request.getSlug());
		newOrganization.setStatus(OrgStatusEnum.PENDING);
		 OrganizationEntity savedOrganization =
                 organizationRepositry.saveAndFlush(newOrganization);
		OrgStatusRequest orgStatus= mapStatusRequest(savedOrganization);
		orgStatusService.createStatus(orgStatus);
		
		
		UserAddRequest user=new UserAddRequest();
		user.setDepartment(Constants.ORGANIZATION_DEPARTMENT_NAME);
		user.setEmail(request.getEmail());
		user.setFirstName(request.getOrganizationName());
		user.setPassword(Constants.ORGANIZATION_HEAD_PASSWORD);
		user.setRoleId(findOrgHeadRole.get().getId());
		user.setOrganizationId(savedOrganization.getId());
		userService.createUser(user);
		
		ApiResponse<Object> res= ApiResponse.success(Constants.ORGANIZATION_REGISTER_SUCCESS, Constants.SUCCESS_CODE);
		
		return res;
		}
		catch(BussinessException ex) {
			throw ex;
		}
		catch(Exception ex) {
			 ex.printStackTrace();
			throw new BussinessException(Constants.ORGANIZATION_REGISTER_FAILURE, Constants.FAILURE_CODE);
		}
	}
	
	@Override
	public ApiResponse<List<OrganizationResponse>> getAllOrganizations() {
		
		List<OrganizationEntity> allorgs=organizationRepositry.findAll();
		List<OrganizationResponse> res=allorgs.stream().map(this::mapOrganizationResponse).toList();
		
		ApiResponse<List<OrganizationResponse>> response=ApiResponse.successWithData(Constants.SUCCESS_MESSAGE, res, Constants.SUCCESS_CODE);
		
		return response;
	}
	@Override
	public ApiResponse<OrganizationResponse> getOrganizationById(Integer id) {
		OrganizationEntity org=organizationRepositry.findById(id).orElseThrow(()->new BussinessException(Constants.ORGANIZATION_NOT_FOUND, Constants.FAILURE_CODE));
		ApiResponse<OrganizationResponse> response=ApiResponse.successWithData(Constants.SUCCESS_MESSAGE, mapOrganizationResponse(org),Constants.SUCCESS_CODE);
		return response;
	}
	@Override
	@Transactional
	public ApiResponse<Object> updateOrganizationInfo(UpdateOrganizationRequest request) {
		OrganizationEntity org=organizationRepositry.findById(request.getId()).orElseThrow(()->new BussinessException(Constants.ORGANIZATION_NOT_FOUND, Constants.FAILURE_CODE));
		
		org.setStatus(request.getStatus());
		org.setUpdatedDate(LocalDateTime.now());
		organizationRepositry.save(org);
		
		OrgStatusRequest orgStatus= mapStatusRequest(org);
		orgStatusService.createStatus(orgStatus);
		
		ApiResponse<Object> res=ApiResponse.success(Constants.ORGANIZATION_UPDATE_SUCCESS,Constants.SUCCESS_CODE);
		return res;
	}
	
	public OrganizationResponse mapOrganizationResponse(OrganizationEntity entity) {
		OrganizationResponse res=new OrganizationResponse();
		res.setCreatedDate(entity.getCreatedDate());
		res.setEmail(entity.getEmail());
		res.setId(entity.getId());
		res.setOrganizationName(entity.getOrgName());
		res.setSlug(entity.getSlug());
		res.setStatus(entity.getStatus().toString());
		res.setUpdatedDate(entity.getUpdatedDate());
		List<OrgStatusResponse> orgStatus=entity.getOrgStatus().stream().map(mapper::mapOrgStatusResponse).toList();
		res.setOrgStatus(orgStatus);
		return res;
		
	}
	public OrgStatusRequest mapStatusRequest(OrganizationEntity entity) {
		OrgStatusRequest orgStatus=new OrgStatusRequest();
		orgStatus.setCreatedDate(LocalDateTime.now());
		orgStatus.setStatus(entity.getStatus());
		orgStatus.setOrganizationId(entity.getId());
		return orgStatus;
	}
}
