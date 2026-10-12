package com.example.demo.requestDto;

import com.example.demo.constants.UserStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

	private Integer userId;
	
	private Integer roleId;
	
	private UserStatusEnum status;
	
	private Integer managerId;
}
