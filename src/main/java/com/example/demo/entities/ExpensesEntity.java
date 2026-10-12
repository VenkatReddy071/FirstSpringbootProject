package com.example.demo.entities;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.constants.ExpensesEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="expenses_entity")
public class ExpensesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	private BigInteger amount;
	
	private String description;
	
	private LocalDateTime expensesDate;
	
	@Enumerated(EnumType.STRING)
	private ExpensesEnum status; 
	
	private LocalDateTime submittedDate;
	
	private LocalDateTime statusUpdatedDate;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime updatedDate;
	
	private String rejectReason;
	
	@OneToOne(fetch =FetchType.LAZY)
	@JoinColumn(name="categorie_id")
	private CategoriesEntity categorie;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name="user_id")
	private UsersEntity users;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="org_id")
	private OrganizationEntity organizationExpenses;
}
