package ru.croc.personnelservice.domain;

import ru.croc.ctp.jxfw.core.generator.meta.XFWEnum;
import ru.croc.ctp.jxfw.core.domain.meta.XFWEnumId;
/**
 * Специализация.
 *
 * @author VMelikhov
 */
@XFWEnum
public enum Specialization {
    /**
     * Терапевт
     */
    @XFWEnumId(1)
    THERAPIST,
    /**
     * Ортопед
     */
    @XFWEnumId(2)
    ORTHOPEDIST,
    /**
     * Хирург
     */
    @XFWEnumId(3)
    SURGEON,
    /**
     * Педиатр(детский врач)
     */
    @XFWEnumId(4)
    PEDIATRICIAN,
    /**
     * Ортодонт
     */
    @XFWEnumId(5)
    ORTHODONTIST
}
