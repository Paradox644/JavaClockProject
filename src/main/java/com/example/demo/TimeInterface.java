package com.example.demo;
public interface TimeInterface {
int GetPrice();
int GetHour();
int GetMin();
int GetSec();
int GetID();
void SetID(int _id);
String GetBrandName();
String GetType();
void SetTime(TIMETYPES timeType,int timeValue) throws CustomTimeExcept;
void IncreaseTime(TIMETYPES timeType, int timeValue) throws CustomTimeExcept;
String GetTime();
String GetString();
}
