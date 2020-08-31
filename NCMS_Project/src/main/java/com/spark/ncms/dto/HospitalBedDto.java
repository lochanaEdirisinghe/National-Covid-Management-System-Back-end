package com.spark.ncms.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HospitalBedDto {
    private int bedId;
    private String patientId;
}
