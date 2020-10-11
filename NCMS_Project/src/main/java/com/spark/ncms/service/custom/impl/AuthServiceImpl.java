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
    public String userCheck(String userId, String password, Connection con) throws SQLException {
        System.out.println(userId+ password);
        User user = authRepository.userCheck(userId, password, con);
        if(user.isIsdoctor()){
            return "doctor"
;        }else if(user.isIsmoh()){
            return "moh";
        }
        return null;
    }
}
