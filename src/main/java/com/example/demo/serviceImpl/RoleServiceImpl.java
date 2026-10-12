package com.example.demo.serviceImpl;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.example.demo.constants.Constants;
import com.example.demo.constants.UserRoleEnum;
import com.example.demo.entities.RoleEntity;
import com.example.demo.exceptions.BussinessException;
import com.example.demo.repositry.RoleRepositry;
import com.example.demo.requestDto.RoleAddRequest;
import com.example.demo.responseDto.RoleResponse;
import com.example.demo.service.RoleService;
import com.example.demo.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

	private final RoleRepositry roleRepositry;
	
	@Override
	public ApiResponse<Object> createRole(RoleAddRequest request) {
		UserRoleEnum roleEnum = UserRoleEnum.valueOf(request.getRoleName());
		Optional<RoleEntity> role=roleRepositry.findByRoleName(roleEnum);
		if(role.isPresent()) {
			throw new BussinessException(Constants.ROLE_ALREADY_EXISTS, Constants.FAILURE_CODE);
		}
		
		RoleEntity newRole=new RoleEntity();
		newRole.setDescription(request.getDescription());
		newRole.setRoleName(roleEnum);
		roleRepositry.save(newRole);
		
		ApiResponse<Object> res=ApiResponse.success(Constants.ROLE_CREATED_SUCCESS,Constants.SUCCESS_CODE);
		return res;
	}
	
	@Override
	public ApiResponse<List<RoleResponse>> getAllRoles() {
		List<RoleEntity> roles=roleRepositry.findAll();
		List<RoleResponse> response=roles.stream().map(this::mapResponse).toList();
		ApiResponse<List<RoleResponse>> res=ApiResponse.successWithData(Constants.SUCCESS_MESSAGE, response, Constants.SUCCESS_CODE);
		return res;
    }
	
	public RoleResponse  mapResponse(RoleEntity role) {
		RoleResponse res=new RoleResponse();
		res.setDescription(role.getDescription());
		res.setId(role.getId());
		res.setRoleName(role.getRoleName());
		
		return res;
	}
}
