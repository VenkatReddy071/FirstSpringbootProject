package com.example.demo.responseDto;

import com.example.demo.constants.UserRoleEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {

	private Integer id;
	private UserRoleEnum roleName;
	private String description;
}
