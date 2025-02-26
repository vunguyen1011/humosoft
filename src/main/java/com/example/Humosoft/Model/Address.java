package com.example.Humosoft.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Embeddable
@Data
@Table(name = "addresses")
public class Address {

	@Column(name = "house_number")
	private String houseNumber; // Số nhà

	@Column(name = "street")
	private String street; // Tên đường

	@Column(name = "city")
	private String city; // Thành phố

	@Column(name = "commune")
	private String commune; // Xã

	@Column(name = "district")
	private String district; // Huyện

	@Column(name = "state")
	private String state; // Bang/Tỉnh (nếu cần)

	@Column(name = "postal_code")
	private String postalCode; // Mã bưu điện

	@Column(name = "country")
	private String country; // Quốc gia

	// Getter and Setter methods
}
