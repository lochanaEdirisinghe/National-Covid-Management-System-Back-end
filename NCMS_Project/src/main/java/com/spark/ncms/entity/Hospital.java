package com.spark.ncms.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Hospital {
    private String id;
    private String name;
    private String district;
    private int location_x;
    private int location_y;

}
