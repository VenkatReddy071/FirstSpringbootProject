package com.example.demo.requestDto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.example.demo.constants.ExpensesEnum;
import com.example.demo.responseDto.ExpensesResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

	private String email;
	private String password;
}
