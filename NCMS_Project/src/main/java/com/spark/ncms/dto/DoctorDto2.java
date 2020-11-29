package com.spark.ncms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto2 {

    private String id;
    private String name;
    private String hospitalId;
    private String isDirector;
    private int contactNo;

}
