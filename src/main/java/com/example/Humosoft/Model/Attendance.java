package com.example.Humosoft.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Nối với User
    private String name;
   
    private String type;    // Loại chấm công (Daily/Hour)
    private LocalDate date;      // Ngày chấm công
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String status;  // Trạng thái (Active/Locked)
    private double totalHours;
    private boolean deleted;
    private String notes;
    
}
