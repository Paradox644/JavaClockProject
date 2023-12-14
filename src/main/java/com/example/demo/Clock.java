package com.example.demo;
public class Clock implements TimeInterface {
    protected String brandName;
    protected int price,minutes,hours,ID;
    public Clock() {
        this.brandName = null;
        this.price = 0;
        this.hours = 0;
        this.minutes = 0;
    }

    public Clock(String _brandName, int _price, int _hours, int _minutes, int _id) {
        this.brandName = _brandName;
        this.price = _price;
        this.hours = _hours;
        this.minutes = _minutes;
        this.ID = _id;
    }
    public Clock(String _brandName, int _price, int _hours, int _minutes) {
        this.brandName = _brandName;
        this.price = _price;
        this.hours = _hours;
        this.minutes = _minutes;
    }
    public int GetSec() {
        return 0;
    }
    public int GetMin(){
        return this.minutes;
    }
    public int GetHour(){
        return this.hours;
    }

    public int GetID(){
        return ID;
    }
    public void SetID(int _id){
        this.ID=_id;
    }
    public int GetPrice() {
        return this.price;
    }
    public String GetType(){
        return ("Hr+Min");
    }
    public String GetBrandName() {
        return this.brandName;
    }
    public void SetTime(TIMETYPES timeType, int timeValue) throws CustomTimeExcept {
    switch (timeType) {
        case TIMETYPES.HOURS:
            if (timeValue<0 || timeValue>23) throw new CustomTimeExcept("ERROR: Hours not in [0,23] range");
            this.hours=timeValue;
            break;
        case TIMETYPES.MINUTES:
            if (timeValue<0 || timeValue>59) throw new CustomTimeExcept("ERROR: Minutes not in [0,59] range");
            this.minutes=timeValue;
            break;
        default:
            throw new CustomTimeExcept("USER INPUT ERROR");
    }
    System.out.println("This "+brandName+" clock with a price of "+price+" have been set to "+timeValue+" "+timeType);

    }

    public void IncreaseTime(TIMETYPES timeType, int timeValue) throws CustomTimeExcept {
        switch (timeType) {
            case TIMETYPES.HOURS:
                if (timeValue<0 || timeValue>23) throw new CustomTimeExcept("ERROR: Hours not in [0,23] range");
                this.hours+=timeValue;
                break;
            case TIMETYPES.MINUTES:
                if (timeValue<0 || timeValue>59) throw new CustomTimeExcept("ERROR: Minutes not in [0,59] range");
                this.minutes+=timeValue;
                break;
            default:
                throw new CustomTimeExcept("USER INPUT ERROR");
        }
        System.out.println("This "+brandName+" clock with a price of "+price+" have been advanced by "+timeValue+" "+timeType);
    }
    public String GetString() {
        return "Time: "+String.format("%02d:%02d",this.hours,this.minutes)+ "\n Brand: "+this.GetBrandName()+ "\nPrice: "+this.GetPrice()+"\n";
    }
    public String GetTime() {
        return String.format("%02d:%02d",this.hours,this.minutes);
    }
}