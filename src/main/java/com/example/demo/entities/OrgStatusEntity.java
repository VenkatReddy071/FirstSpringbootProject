package com.example.demo.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.constants.OrgStatusEnum;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="org_status_table")
public class OrgStatusEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrgStatusEnum status;
	
	private LocalDateTime createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name="organization_id")
	private OrganizationEntity organizationStatus;
	
}
