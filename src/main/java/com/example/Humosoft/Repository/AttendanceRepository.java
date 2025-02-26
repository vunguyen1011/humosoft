package com.example.Humosoft.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Humosoft.Model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {

}
