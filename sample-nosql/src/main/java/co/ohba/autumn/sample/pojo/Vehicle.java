package co.ohba.autumn.sample.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NoSql(dataFormat=DataFormatType.MAPPED)
public class Vehicle {
	
	@Id @GeneratedValue
	@Field(name="_id") 
	private String id;

	@NotNull
	private String make;
	private String model;
	
	private String color;
	
}
