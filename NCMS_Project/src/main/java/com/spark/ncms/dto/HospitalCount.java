package com.spark.ncms.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HospitalCount {
    private String hospitalId;
    private String hospitalName;
    private int Count;
}
