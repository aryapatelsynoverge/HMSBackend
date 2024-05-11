package com.system.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.system.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_mst")
public class Patient extends Auditable<String> implements Serializable {

    @Id  //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "blood_group", nullable = false)
    private String bloodGroup;

    @Column(name = "phone_number", nullable = false)
    private Long phoneNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "zipcode", nullable = false)
    private int zipcode;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "patient_custom_id")
    private String patientId;

    @Column(name = "documents" , columnDefinition = "json")
    private String documents;

}
