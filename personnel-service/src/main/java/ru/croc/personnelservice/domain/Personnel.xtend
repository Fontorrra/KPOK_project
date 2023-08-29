package ru.croc.personnelservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import ru.croc.ctp.jxfw.core.generator.meta.XFWObject;
import ru.croc.ctp.jxfw.core.domain.meta.persistence.XFWOneToMany;
import ru.croc.ctp.jxfw.core.domain.meta.persistence.XFWOneToOne;
import java.util.Set;
import java.util.List;

@XFWObject
@Table(name = "personnel_table")
public class Personnel{
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personnel_table_seq")
    @SequenceGenerator(name = "personnel_table_seq", sequenceName = "personnel_table_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String fatherName;

    @Column(nullable = false)
    private Integer experience;

    @Column(nullable = false)
    private Specialization specialization;

    @Column(nullable = false)
    private Integer clinicExperience;

    private String additionalInfo;
}