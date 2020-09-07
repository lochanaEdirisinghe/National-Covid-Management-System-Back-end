package com.spark.ncms.service.custom;

import java.sql.Connection;
import java.sql.SQLException;

public interface AuthService {

    String userCheck(String username , String password, Connection con) throws SQLException;
}
