package com.example.Humosoft.Repository;

import java.lang.foreign.Linker.Option;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Humosoft.Model.Attendance;
import com.example.Humosoft.Model.User;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {
	boolean existsByUserAndDate(User user,LocalDate date);
	List<Attendance> findAllByUserIn(List<User> users);
	Optional<Attendance> findByUserAndDate(User user,LocalDate date);
}
