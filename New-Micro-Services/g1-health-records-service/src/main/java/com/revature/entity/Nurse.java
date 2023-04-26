package com.revature.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Nurse {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID" ,unique = true)
    int id;
    @Column(name = "NURSE_NAME")
    private String nurseName;

    @Id
    @Column(name = "NURSE_EMAIL")
    private String nurseEmail;

    @Transient
    private String nurseRole;
}
