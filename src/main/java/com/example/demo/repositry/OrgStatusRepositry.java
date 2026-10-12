package com.example.demo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.OrgStatusEntity;

@Repository
public interface OrgStatusRepositry extends JpaRepository<OrgStatusEntity, Integer>{

	

	List<OrgStatusEntity> findByOrganizationStatus_id(Integer id);;
}
