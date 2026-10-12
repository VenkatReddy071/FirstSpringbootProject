package com.example.demo.responseDto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationResponse {

	private Integer id;
	private String organizationName;
	
	private String email;
	
	private String slug;
	
    private LocalDateTime createdDate;
	
	private LocalDateTime updatedDate;
	
	private String status;
	
	private List<OrgStatusResponse> orgStatus;
}
