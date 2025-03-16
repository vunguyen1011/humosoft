package com.example.Humosoft.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Humosoft.Model.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {
	 Optional<LeaveType> findByLeaveTypeName(String leaveTypeName);
}
