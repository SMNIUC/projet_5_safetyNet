package com.safetynet.safetynetalerts.model;

import lombok.Data;

import java.util.Date;

@Data
public class Person {

    private String firstName;
    private String lastName;
    private String city;
    private int zip;
    private String phoneNumber;
    private String email;
    private Date birthday;

}

