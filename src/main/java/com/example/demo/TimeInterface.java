package com.example.demo;
public interface TimeInterface {
public int GetPrice();
public int GetHour();
public int GetMin();
public int GetSec();
public int GetID();
public void SetID(int _id);
public String GetBrandName();
public String GetType();
public void SetTime(TIMETYPES timeType,int timeValue) throws CustomTimeExcept;
public void IncreaseTime(TIMETYPES timeType, int timeValue) throws CustomTimeExcept;
public String GetTime();
public String GetString();
}
