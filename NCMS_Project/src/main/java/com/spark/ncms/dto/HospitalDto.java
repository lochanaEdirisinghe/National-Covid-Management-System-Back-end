package com.spark.ncms.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HospitalDto {
    private String id;
    private String name;
    private String district;
    private int locationX;
    private int locationY;
}
