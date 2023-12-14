package com.example.demo;

public class ClockBuilder {
    public static boolean boolDB = true;
    static ClockStore c = new ClockStore(boolDB);
    public static ClockStore build(){
        return c;
    }

}