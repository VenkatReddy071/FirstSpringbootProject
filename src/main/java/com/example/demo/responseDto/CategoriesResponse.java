package com.example.demo.responseDto;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesResponse {

    private Integer id;
	
	private String name;
	
	private String description;
	
	private Boolean active;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime updatedDate;
}
