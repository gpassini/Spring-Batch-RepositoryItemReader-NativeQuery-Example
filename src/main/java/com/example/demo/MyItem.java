package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "item")
public class MyItem {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private Long status;
}
