package com.spark.ncms.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

     private String firstName;
     private String lastName;
     private int age;
     private int locationX;
     private int locationY;
     private String district;
     private String gender;
     private String contactNo;
     private String email;
}
