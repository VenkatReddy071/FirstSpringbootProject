package com.example.demo.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.example.demo.constants.UserStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_table")
public class UsersEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String firstName;
	
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	
	private String employeeCode;
	
	@Column(nullable = false)
	private String department;
	
	@Enumerated(EnumType.STRING)
	private UserStatusEnum status;
	
    private LocalDateTime createdDate;
	
	private LocalDateTime updatedDate;
	private Boolean isManager;
	
	@ManyToOne(fetch =FetchType.LAZY,optional = false)
	@JoinColumn(name = "organization_id",nullable = false)
	private OrganizationEntity organization;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="manager_id")
	private UsersEntity manager;
	
	@OneToMany(mappedBy = "manager")
	@Builder.Default
	private List<UsersEntity> users=new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY,orphanRemoval = false,mappedBy = "users")
	private List<ExpensesEntity> expenses=new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="role_id")
	private RoleEntity userRole;
}
