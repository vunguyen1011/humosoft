package com.example.Humosoft.DTO.Request;

import java.sql.Date;

import lombok.Data;
@Data
public class UserRequest {

    private String fullName;
    private String email;
    private String phone;
    private Date dateOfBirth;
    private String gender;
    private String image;
    private String username;
    private String password;
    private boolean status;

    // Các trường địa chỉ trong UserRequest
    private String houseNumber;  // Số nhà
    private String street;       // Tên đường
    private String city;         // Thành phố
    private String commune;      // Xã
    private String district;     // Huyện
    private String state;        // Bang/Tỉnh
    private String postalCode;   // Mã bưu điện
    private String country;      // Quốc gia
}
