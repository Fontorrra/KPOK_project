package ru.croc.patientservice.domain;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import ru.croc.ctp.jxfw.core.generator.meta.XFWObject;

@XFWObject

@Table(name = "patient_table")
public class Patient {

    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_table_seq")
    @SequenceGenerator(name = "patient_table_seq", sequenceName = "patient_table_seq", allocationSize = 1)
    private Long id;


    /**
     * Имя.
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * Фамилия.
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * Отчество.
     */
    private String fatherName;


    /**
     * Возраст.
     */
    @Column(nullable = false)
    private Integer age;

    /**
     * Адрес.
     */
    @Column(nullable = false)
    private String address;

    /**
     * Телефон.
     */
    @Column(nullable = false)
    private String phone;

    /**
     * email.
     */
    private String email;



}

