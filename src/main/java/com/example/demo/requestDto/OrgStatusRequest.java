package com.example.demo.requestDto;

import java.time.LocalDateTime;

import com.example.demo.constants.OrgStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrgStatusRequest {

	private OrgStatusEnum status;
	private LocalDateTime createdDate;
	private Integer organizationId;
}
