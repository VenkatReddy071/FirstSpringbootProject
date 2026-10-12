package com.example.demo.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddRequest {

    private String firstName;
	
	private String email;
	
	private String password;
	
	private String employeeCode;
	
	private String department;
	
	private Integer managerId;
	
	private Integer roleId;
	
	private Boolean isManager;
	
	private Integer organizationId;
}
