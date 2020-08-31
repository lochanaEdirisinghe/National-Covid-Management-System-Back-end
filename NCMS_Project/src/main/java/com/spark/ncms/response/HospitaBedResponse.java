package com.spark.ncms.response;

import com.spark.ncms.dto.HospitalBedDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitaBedResponse {
    private String hospitalId;
    private List<HospitalBedDto> hospitalBeds;
}
