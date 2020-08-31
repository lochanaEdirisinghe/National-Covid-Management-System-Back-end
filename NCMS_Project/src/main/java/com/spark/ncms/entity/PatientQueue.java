package com.spark.ncms.entity;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientQueue {
    private int queueId;
    private String patientId;
}
