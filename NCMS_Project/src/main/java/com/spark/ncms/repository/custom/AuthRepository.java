package com.spark.ncms.repository.custom;

import com.spark.ncms.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface AuthRepository {
    User userCheck(String username , String password, Connection con) throws SQLException;
}
