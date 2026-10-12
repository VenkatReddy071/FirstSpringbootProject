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
public class LoginResponse {

	private String token;
	private String email;
}
