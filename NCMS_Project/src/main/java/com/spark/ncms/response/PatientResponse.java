package com.spark.ncms.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    private String serial_no;
    private int bed_no;
    private int queue_no;
}
