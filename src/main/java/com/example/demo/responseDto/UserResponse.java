package com.example.demo.responseDto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	
    private Integer id;
	
	private String firstName;
	
	private String email;
	
	private String employeeCode;
	
	private LocalDateTime createdDate;
		
	private LocalDateTime updatedDate;
	
	private Integer managerId;

	private String managerName;
	
	private String roleName;
	
}
