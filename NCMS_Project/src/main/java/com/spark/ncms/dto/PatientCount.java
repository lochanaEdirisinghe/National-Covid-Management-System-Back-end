package com.spark.ncms.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientCount {
    private int activecount;
    private int discharged;
}
