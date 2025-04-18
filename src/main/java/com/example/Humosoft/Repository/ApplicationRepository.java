package com.example.Humosoft.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Humosoft.Model.Application;
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
	Optional<Application> findById(Integer id);
	
	

}
