package com.example.demo.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.constants.OrgStatusEnum;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumeratedValue;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="organization_table")
public class OrganizationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String orgName;
	
	private String email;
	
	@Column(unique = true,updatable = false)
	private String slug;
	
	@Enumerated(EnumType.STRING)
	private OrgStatusEnum status;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime updatedDate;
	
	@OneToMany(mappedBy ="organization",fetch = FetchType.LAZY)
	private List<UsersEntity> users=new ArrayList<>();
	
	@OneToMany(mappedBy="organizationCategories",fetch = FetchType.LAZY,orphanRemoval = false)
	private List<CategoriesEntity> categories=new ArrayList<>();
	
	@OneToMany(mappedBy="organizationStatus",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<OrgStatusEntity> orgStatus=new ArrayList<>();
	
	@OneToMany(mappedBy = "organizationExpenses",fetch = FetchType.LAZY)
	private List<ExpensesEntity> expenses=new ArrayList<>();
	
}
