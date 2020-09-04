package com.spark.ncms.response;

import com.spark.ncms.dto.HospitalBedDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HospitalBedRespDto {
    private int bedId;
    private String patientId;
    private boolean isAdmitted;
    private boolean isDischarged;

}
