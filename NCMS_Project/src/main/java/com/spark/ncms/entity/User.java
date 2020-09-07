package com.spark.ncms.entity;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private String username;
    private String password;
    private String namel;
    private boolean moh;
    private boolean hospital;
}
