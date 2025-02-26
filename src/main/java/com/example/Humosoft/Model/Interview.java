package com.example.Humosoft.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @ManyToOne
    @JoinColumn(name = "interviewer_id")
    private User interviewer;

    private Date interviewDate;
    private String location;
    private String status;
    private String notes;
    private String result;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    // Getters and Setters
}

