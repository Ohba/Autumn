package co.ohba.autumn.sample.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehicle {
	
	@Id @GeneratedValue
	private Long id;

	@NotNull
	private String make;
	private String model;
	
	private String color;
	
}
