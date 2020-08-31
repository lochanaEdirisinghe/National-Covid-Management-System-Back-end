package com.spark.ncms.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HospitalBed {
    private int bedId;
    private String hospitalId;
    private String patientId;
}
