package com.example.demo.serviceImpl;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.demo.constants.Constants;

import com.example.demo.constants.UserStatusEnum;
import com.example.demo.entities.OrganizationEntity;
import com.example.demo.entities.RoleEntity;
import com.example.demo.entities.UsersEntity;
import com.example.demo.exceptions.BussinessException;
import com.example.demo.repositry.OrganizationRepositry;
import com.example.demo.repositry.RoleRepositry;
import com.example.demo.repositry.UserRepositry;
import com.example.demo.requestDto.LoginRequest;
import com.example.demo.requestDto.UpdateUserRequest;
import com.example.demo.requestDto.UserAddRequest;
import com.example.demo.responseDto.LoginResponse;
import com.example.demo.responseDto.UserResponse;
import com.example.demo.secutiry.JWTService;
import com.example.demo.service.UsersService;
import com.example.demo.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UsersService{

	private final UserRepositry userRepositry;
	private final RoleRepositry roleRepositry;
	private final OrganizationRepositry organizationRepositry;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	
	@Override
	public ApiResponse<Object> activeOrDeactiveUser(Integer id) {
	
		UsersEntity user=userRepositry.findById(id).orElseThrow(()->new BussinessException(Constants.USER_NOT_FOUND, Constants.FAILURE_CODE));
		if(user.getStatus().equals(UserStatusEnum.ACTIVE)) {
			user.setStatus(UserStatusEnum.SUSPENDED);
		}
		else {
			user.setStatus(UserStatusEnum.ACTIVE);
		}
		user.setUpdatedDate(LocalDateTime.now());
		userRepositry.save(user);
		
		ApiResponse<Object> res=ApiResponse.success(Constants.USER_UPDATED_SUCCESS, Constants.SUCCESS_CODE);
		
		return res;

	}
	@Override
	public ApiResponse<Object> createUser(UserAddRequest request) {
		Optional<UsersEntity> user=userRepositry.findByEmail(request.getEmail());
		
		if(user.isPresent()) {
			throw new BussinessException(Constants.USER_ALREADY_EXISTS, Constants.FAILURE_CODE);
		}
		UsersEntity managerUser = null;
		if(request.getManagerId() != null) {
			managerUser=userRepositry.findById(request.getManagerId()).orElseThrow(()->new BussinessException(Constants.USER_NOT_FOUND, Constants.FAILURE_CODE));
		}
		RoleEntity role=roleRepositry.findById(request.getRoleId()).orElseThrow(()->new BussinessException(Constants.ROLE_NOT_FOUND, Constants.FAILURE_CODE));
		OrganizationEntity organizationEntity=organizationRepositry.findById(request.getOrganizationId()).orElseThrow(()-> new BussinessException(Constants.ORGANIZATION_NOT_FOUND, Constants.FAILURE_CODE));
		
		UsersEntity newUser= new UsersEntity();
		newUser.setCreatedDate(LocalDateTime.now());
		newUser.setDepartment(request.getDepartment());
		newUser.setEmail(request.getEmail());
		newUser.setEmployeeCode(request.getEmployeeCode());
		newUser.setFirstName(request.getFirstName());
		newUser.setIsManager(request.getIsManager());
		newUser.setManager(managerUser);
		newUser.setStatus(UserStatusEnum.ACTIVE);
		newUser.setPassword(passwordEncoder.encode(request.getPassword()));
		newUser.setUserRole(role);
		newUser.setOrganization(organizationEntity);
		
		userRepositry.save(newUser);
		
		ApiResponse<Object> res=ApiResponse.success(Constants.USER_ADDED_SUCCESS, Constants.SUCCESS_CODE);
		return res;
	}
	
	@Override
	

	public ApiResponse<Object> updateUser(UpdateUserRequest request) {
		UsersEntity user=userRepositry.findById(request.getUserId()).orElseThrow(()->new BussinessException(Constants.USER_NOT_FOUND, Constants.FAILURE_CODE));
		UsersEntity managerUser = null;
		if(request.getManagerId() != null) {
			managerUser=userRepositry.findById(request.getManagerId()).orElseThrow(()->new BussinessException(Constants.USER_NOT_FOUND, Constants.FAILURE_CODE));
		}
		RoleEntity role=roleRepositry.findById(request.getRoleId()).orElseThrow(()->new BussinessException(Constants.ROLE_NOT_FOUND, Constants.FAILURE_CODE));
		user.setManager(managerUser);
		user.setUserRole(role);
		user.setStatus(request.getStatus());
		user.setUpdatedDate(LocalDateTime.now());
		userRepositry.save(user);
		ApiResponse<Object> res=ApiResponse.success(Constants.USER_UPDATED_SUCCESS, Constants.SUCCESS_CODE);
		return res;
	}
	
	@Override
	public ApiResponse<List<UserResponse>> getEmployeesUnderManager(Integer id) {
		UsersEntity user=userRepositry.findById(id).orElseThrow(()->new BussinessException(Constants.USER_NOT_FOUND, Constants.FAILURE_CODE));
		List<UserResponse> users=user.getUsers().stream().map(this::mapUserResponse).toList();
		ApiResponse<List<UserResponse>>response=ApiResponse.successWithData(Constants.SUCCESS_MESSAGE, users, Constants.SUCCESS_CODE);
		return response;
	}
	
	@Override
	public ApiResponse<List<UserResponse>> getUsersByOrganization(Integer id) {
		OrganizationEntity organizationEntity=organizationRepositry.findById(id).orElseThrow(()-> new BussinessException(Constants.ORGANIZATION_NOT_FOUND, Constants.FAILURE_CODE));
		List<UserResponse> users=organizationEntity.getUsers().stream().map(this::mapUserResponse).toList();
		ApiResponse<List<UserResponse>>response=ApiResponse.successWithData(Constants.SUCCESS_MESSAGE, users, Constants.SUCCESS_CODE);
		return response;
	}
	
	@Override
	public ApiResponse<LoginResponse> login(LoginRequest request) {
		UsersEntity user=userRepositry.findByEmail(request.getEmail()).orElseThrow(()->new BussinessException(Constants.USER_NOT_FOUND, Constants.FAILURE_CODE));
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new BussinessException(Constants.INVALID_CREDENTAILS, Constants.FAILURE_CODE);
		}
		 authenticationManager.authenticate(
		            new UsernamePasswordAuthenticationToken(
		                request.getEmail(),
		                request.getPassword()
		            )
		        );
		  String token = jwtService.generateToken(user);
		  LoginResponse res=new LoginResponse();
		  res.setEmail(user.getEmail());
		  res.setToken(token);
		  
		  ApiResponse<LoginResponse> response=ApiResponse.successWithData(Constants.SUCCESS_MESSAGE, res, Constants.SUCCESS_CODE);
		return response;
	}
	
	public UserResponse mapUserResponse(UsersEntity user) {
		UserResponse res= new UserResponse();
		res.setCreatedDate(user.getCreatedDate());
		res.setEmail(user.getEmail());
		res.setEmployeeCode(user.getEmployeeCode());
		res.setFirstName(user.getFirstName());
		res.setId(user.getId());
		if(user.getManager()!=null) {
			res.setManagerId(user.getManager().getId());
			res.setManagerName(user.getManager().getFirstName());
		}
		
		res.setRoleName(user.getUserRole().getRoleName().toString());
		res.setUpdatedDate(user.getUpdatedDate());
		return res;
	}
	
	
	
	
}
