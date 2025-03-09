package com.example.Humosoft.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Humosoft.Model.Paygrade;

public interface PayGradeRepository extends JpaRepository<Paygrade, Integer>{
	Optional<Paygrade> findByPaygradeName(String paygradeName);
	boolean existsByPaygradeName(String paygradeName);
	 List<Paygrade> findByDeletedFalse();  
}
