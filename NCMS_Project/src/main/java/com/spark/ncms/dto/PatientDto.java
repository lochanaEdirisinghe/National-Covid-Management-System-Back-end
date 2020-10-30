package com.spark.ncms.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientDto {

     private String patientId;
     private String firstName;
     private String lastName;
     private String district;
     private int locationX;
     private int locationY;
     private String severityLevel;
     private String gender;
     private String contactNo;
     private String email;
     private int age;
     private String admitDate;
     private String admittedBy;
     private String dischargedDate;
     private String dischargedBy;
     private int bedId;
     private String hospitalId;
     private int queueNo;

}
