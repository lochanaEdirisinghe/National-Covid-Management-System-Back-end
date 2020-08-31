package com.spark.ncms.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Doctor {
    private String id;
    private String name;
    private String hospital_id;
    private boolean is_director;
}
