package com.example.Humosoft.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "leave_type_id")
    private LeaveType leaveType;

    private Date startDate;
    private Date endDate;
    private int totalDays;
    private String status;
    private String reason;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    private Date approvedAt;
    
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    private int leaveBalances;

    // Getters and Setters
}
