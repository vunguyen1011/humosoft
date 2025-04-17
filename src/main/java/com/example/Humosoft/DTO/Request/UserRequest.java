package com.example.Humosoft.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.sql.Date;

import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Full name cannot be empty")
    private String fullName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    private String phone;

    @NotNull(message = "Date of birth cannot be null")
    private Date dateOfBirth;

    @NotBlank(message = "Gender cannot be empty")
    private String gender;

    private String image;

    @NotNull(message = "Status cannot be null")
    private boolean status;

    // Các trường địa chỉ trong UserRequest
    @NotBlank(message = "House number cannot be empty")
    private String houseNumber;  // Số nhà

    @NotBlank(message = "Street cannot be empty")
    private String street;       // Tên đường

    @NotBlank(message = "City cannot be empty")
    private String city;         // Thành phố

    @NotBlank(message = "Commune cannot be empty")
    private String commune;      // Xã

    @NotBlank(message = "District cannot be empty")
    private String district;     // Huyện

    @NotBlank(message = "State cannot be empty")
    private String state;        // Bang/Tỉnh

    @NotBlank(message = "Postal code cannot be empty")
    private String postalCode;   // Mã bưu điện

    @NotBlank(message = "Country cannot be empty")
    private String country;      // Quốc gia

    // Vị trí và phòng ban
    @NotBlank(message = "Position name cannot be empty")
    private String positionName;

    @NotBlank(message = "Department name cannot be empty")
    private String departmentName;
    
    private String username;
}
