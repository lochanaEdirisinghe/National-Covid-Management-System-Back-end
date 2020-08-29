package com.spark.ncms.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Patient {

    private String id;
    private String firstName;
    private String lastName;
    private String district;
    private int locationX;
    private int locationY;
    private String severity_level;
    private String gender;
    private String contact;
    private String email;
    private int age;
    private String admit_date;
    private String admitted_by;
    private String discharge_date;
    private String discharged_by;
}
