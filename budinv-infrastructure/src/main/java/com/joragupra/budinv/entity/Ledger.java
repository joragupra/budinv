package com.joragupra.budinv.entity;

import javax.persistence.*;

@Entity
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
}
