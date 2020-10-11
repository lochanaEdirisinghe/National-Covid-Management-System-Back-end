package com.spark.ncms.util;

import com.spark.ncms.constants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

public class JWTUtil {
    public String createToken(String userName) {
        //get the current time
        Date today = new Date();
        // add token expiration time /days- hours- minits or seconds
        Calendar instance = Calendar.getInstance();
        //set current date
        instance.setTime(today);
        //add 10 minits to the current time
        instance.add(Calendar.MINUTE, 4);
        //token expire time
        Date exTime = instance.getTime();

        //generate secret key for token
        SecretKey secretKey = Keys.hmacShaKeyFor(Constants.JWT_SECRET.getBytes());

        //create a token
        String compact = Jwts.builder()
                .setIssuer("lochana")
                .setSubject(userName)
                .setIssuedAt(today)
                .setExpiration(exTime)
                .claim("anything", "Hello There").
                        signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        return compact;
    }


    public Jws<Claims> getClaims(String jws) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Constants.JWT_SECRET.getBytes());
        try {
            String token = jws.split(" ")[1];
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        } catch (Exception ex) {
            return null;
        }

    }

    public boolean checkValidity(String jws) {
        Jws<Claims> claims = getClaims(jws);
        if (claims != null) {
            Date expiration = claims.getBody().getExpiration();
            Date today = new Date();
            return today.before(expiration);
        } else {
            return false;
        }
    }

    public String getUserName(String jwt) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Constants.JWT_SECRET.getBytes());
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody().getSubject();
    }
}
