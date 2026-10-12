package com.example.demo.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRegiserRequest {

	@NotBlank(message="Organization name is required")
	private String organizationName;
	
	@NotBlank(message="Email is required")
	@Email(message="Enter a valid email")
	private String email;
	
	@NotBlank(message="Enter a valid slug")
	private String slug;
	
}
