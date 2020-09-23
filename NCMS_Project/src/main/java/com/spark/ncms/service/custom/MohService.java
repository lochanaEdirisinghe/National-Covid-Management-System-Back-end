package com.spark.ncms.service.custom;

import com.spark.ncms.dto.HospitalBedDto;
import com.spark.ncms.dto.HospitalDto;
import com.spark.ncms.dto.QueueDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MohService extends SuperSevice {
    List<QueueDto> getQueueDetails(Connection con) throws SQLException, ClassNotFoundException;
    boolean addNewHospital(HospitalDto hospitalDto, Connection con) throws SQLException, ClassNotFoundException;
    List<HospitalBedDto> getBedDetails(Connection con) throws SQLException, ClassNotFoundException;
}
