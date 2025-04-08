package com.example.Humosoft.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Humosoft.Model.Recruitment;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer>{
	boolean existsById(Integer id); // Kiểm tra xem có tồn tại bản ghi với id này không

}
