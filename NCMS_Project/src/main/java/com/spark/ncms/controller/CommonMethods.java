package com.spark.ncms.controller;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CommonMethods {

    public static void responseProcess(HttpServletRequest req, HttpServletResponse resp, String responseJson)  {
        PrintWriter out = null;
        try {
            out = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
 /*       String origin = req.getHeader("Origin");
        resp.setHeader("Access-Control-Allow-Origin", origin);
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setHeader("Access-Control-Allow-Methods", "*");*/

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(responseJson);
        out.flush();
    }

    public static JsonObject getJsonObject(HttpServletRequest req){
        ServletInputStream inputStream = null;
        try {
            inputStream = req.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonReader reader = Json.createReader(inputStream);
        JsonObject jsonObject = reader.readObject();
        return jsonObject;
    }

}
