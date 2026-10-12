package com.example.demo.requestDto;

import com.example.demo.constants.UserRoleEnum;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAddRequest {

	@NotNull(message="role is required")
	private String roleName;
	
	@NotNull(message="description is required")
	private String description;
}
