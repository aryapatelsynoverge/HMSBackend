package com.system.model;


import com.system.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hospital_mst")
public class Hospital extends Auditable<String> implements Serializable {

    @Id  //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "zipcode", nullable = false)
    private int zipCode;

    @Column(name = "hospital_type")
    @Enumerated(EnumType.STRING)
    private HospitalType hospitalType;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "hospital_custom_id")
    private String hospitalId;

}
