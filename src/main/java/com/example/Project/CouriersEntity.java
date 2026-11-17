package com.example.Project;

import jakarta.persistence.*;

@Entity
@Table(name = "couriers")
public class CouriersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "passportnumber")
    private String passportNumber;

    @Column(name = "phone")
    private String phone;

    @Column(name = "workingtime", columnDefinition = "tstzrange")
    private String workingTime;

    @Column(name = "workdistrict")
    private String workDistrict;

    public CouriersEntity() {}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getPhone() {
        return phone;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public String getWorkDistrict() {
        return workDistrict;
    }
}
