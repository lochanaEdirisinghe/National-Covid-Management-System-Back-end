package com.spark.ncms.service;

import com.spark.ncms.dto.HospitalDto;
import com.spark.ncms.dto.QueueDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MohService {
    List<QueueDto> getQueueDetails(Connection con) throws SQLException, ClassNotFoundException;
    boolean addNewHospital(HospitalDto hospitalDto, Connection con) throws SQLException, ClassNotFoundException;
}
