package com.example.Humosoft.Model;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.cglib.core.Local;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class TimeOff {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	   @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;
	   @ManyToOne
	    @JoinColumn(name = "leave_type_id")
	    private LeaveType leaveType;
	   private LocalDate startDate;
	    private LocalDate endDate;
	    private int totalDays;
	    private String status;
	    private String reason;
	    private LocalDate approvedAt;
	    
	    @Column(name = "created_at")
	    private LocalDate createdAt;
}
