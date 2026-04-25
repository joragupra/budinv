package com.joragupra.budinv.entity;

import jakarta.persistence.*;

@Entity
public class Ledger {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
}
