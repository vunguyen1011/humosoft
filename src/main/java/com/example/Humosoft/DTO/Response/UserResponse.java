package com.example.Humosoft.DTO.Response;

import java.util.*;

import lombok.Builder;
import lombok.Data;
@Data
@Builder

public class UserResponse {

    private String fullName;
    private String email;
    private String phone;
    private Date dateOfBirth;
    private String gender;
    private String positionName;
    private String departmentName;
    
}
