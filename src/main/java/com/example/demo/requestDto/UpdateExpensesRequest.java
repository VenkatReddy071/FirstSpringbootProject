package com.example.demo.requestDto;

import com.example.demo.constants.ExpensesEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateExpensesRequest {

	private Integer id;
	
	private ExpensesEnum status;
	
	private String rejectReason;
}
