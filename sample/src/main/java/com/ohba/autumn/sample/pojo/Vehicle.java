package com.ohba.autumn.sample.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehicle {
	
	@Id
	private Long id;

	private String make;
	private String model;
	
}
