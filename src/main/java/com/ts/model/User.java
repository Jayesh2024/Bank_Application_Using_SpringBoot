package com.ts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//I'td use for create unique value for particular column
//@Table(name = "user",
//uniqueConstraints = {@UniqueConstraint(columnNames = {"aadhar", "pan", "email"})}) 

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto generated unique value
	private Long id;
	private String name;
	private String aadhar;
	private String pan;
	private String email;
}
