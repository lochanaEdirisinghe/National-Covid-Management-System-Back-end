package com.spark.ncms.service.custom.impl;

import com.spark.ncms.entity.User;
import com.spark.ncms.repository.RepoFactory;
import com.spark.ncms.repository.RepoType;
import com.spark.ncms.repository.custom.AuthRepository;
import com.spark.ncms.service.ServiceFactory;
import com.spark.ncms.service.custom.AuthService;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {

    private AuthRepository authRepository;

    public AuthServiceImpl(){
        authRepository= RepoFactory.getInstance().getRepo(RepoType.USER);
    }

    @Override
    public String userCheck(String username, String password, Connection con) throws SQLException {
        User user = authRepository.userCheck(username, password, con);
        if(user.isHospital()){
            return "hospital"
;        }else if(user.isMoh()){
            return "moh";
        }
        return null;
    }
}
