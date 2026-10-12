package com.example.demo.mapper;


import org.springframework.stereotype.Component;

import com.example.demo.entities.OrgStatusEntity;
import com.example.demo.responseDto.OrgStatusResponse;

@Component
public class CommonMethodsMapper {

	public OrgStatusResponse mapOrgStatusResponse(OrgStatusEntity status) {
		OrgStatusResponse response= new OrgStatusResponse();
		response.setCreatedDate(status.getCreatedDate());
		response.setId(status.getId());
		response.setStatus(status.getStatus());
		
		return response;
	}
}
