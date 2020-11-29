package com.spark.ncms.dto;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.TreeMap;

class Customer{

    private int code;
    private double salary;
    Customer(int code, double salary) {
        this.code = code;
        this.salary = salary;
    }
    @Override
    public String toString() {
        return "C" + getCode() + " - " + getSalary();
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }
}
class Demo {
    public static void main(String args[]) {
        Hashtable<Integer, Customer> hm=new Hashtable<>();
        hm.put(5, new Customer(1001,34000));
        hm.put(2, new Customer(1002,24000));
        hm.put(7, new Customer(1003,54000));
        hm.put(1, new Customer(1004,634000));
        System.out.println(hm);


    }
}