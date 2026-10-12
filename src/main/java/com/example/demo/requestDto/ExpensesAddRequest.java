package com.example.demo.requestDto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.example.demo.constants.ExpensesEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesAddRequest {

    private BigInteger amount;
 	
	private String description;
	
	private LocalDateTime expensesDate;
	
	private Integer categoryId;
	
	
	private ExpensesEnum status;
}
