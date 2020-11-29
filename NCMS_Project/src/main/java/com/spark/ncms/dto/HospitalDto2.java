package com.spark.ncms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDto2 {
    private String id;
    private String name;
    private String district;
    private int bedCount;
    private int locationX;
    private int locationY;
}
