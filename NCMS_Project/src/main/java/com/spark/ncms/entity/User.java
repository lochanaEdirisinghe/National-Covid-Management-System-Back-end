package com.spark.ncms.entity;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private String userId;
    private String password;
    private boolean ismoh;
    private boolean isdoctor;
}
