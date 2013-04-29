package co.ohba.autumn.sample.pojo;

import co.ohba.autumn.HasID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehicle implements HasID<Long> {
	
	@Id @GeneratedValue
	private Long id;

	@NotNull
	private String make;
	private String model;
	
	private String color;
	
}
