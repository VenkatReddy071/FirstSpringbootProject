package com.example.demo.responseDto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.example.demo.constants.ExpensesEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesResponse {

	private Integer id;
	
    private BigInteger amount;
 	
	private String description;
	
	private LocalDateTime expensesDate;
	
	private Integer categoryId;
	
	private String categoryName;
	
	private ExpensesEnum status;
	
	private String userName;
	
	private String userEmail;
	
	private Integer userId;
	
	private String organizationName;
	
    private LocalDateTime submittedDate;
	
	private LocalDateTime statusUpdatedDate;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime updatedDate;
	
	private String rejectReason;
}
