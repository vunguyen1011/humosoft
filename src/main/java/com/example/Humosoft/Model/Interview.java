	package com.example.Humosoft.Model;
	
	import java.util.Date;
	
	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import lombok.*;
	
	@Entity
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public class Interview {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	
	    private Integer recruitment;
	
	    private Integer interviewer;
	
	    private Date interviewDate;
	    private String location;
	    private String status="PENDING";
	    private String notes;

	
	    @Column(name = "created_at")
	    private Date createdAt=new Date();	

	
	}
	
