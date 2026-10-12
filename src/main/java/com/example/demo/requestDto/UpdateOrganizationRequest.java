package com.example.demo.requestDto;

import com.example.demo.constants.OrgStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrganizationRequest {

	private Integer id;
	
	private OrgStatusEnum status;
	
}
