package com.example.demo.repositry;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.ExpensesEntity;

@Repository
public interface ExpensesRepositry extends JpaRepository<ExpensesEntity, Integer>{

	 Optional<ExpensesEntity> findByCategorie_IdAndAmountAndUsers_Id(
	            Integer categorieId,
	            BigInteger amount,
	            Integer userId
	    );
}
