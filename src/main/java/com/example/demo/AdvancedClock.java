package com.example.demo;
import java.io.Serializable;

class AdvancedClock extends Clock implements TimeInterface{
    protected int seconds;
    public AdvancedClock(){
        super();
        this.seconds = 0;
    }
    public AdvancedClock(String _brandName, int _price,int _hours, int _minutes, int _seconds, int _id) {
        super(_brandName,_price, _hours, _minutes, _id);
        this.seconds=_seconds;

    }
@Override
public int GetSec(){
        return this.seconds;
}
    @Override
    public String GetType() {
        return ("Hr+Min+Sec");
    }
    @Override
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
            case TIMETYPES.SECONDS:
                if (timeValue<0 || timeValue>59) throw new CustomTimeExcept("ERROR: Seconds not in [0,59] range");
                this.seconds=timeValue;
                break;
            default:
                throw new CustomTimeExcept("USER INPUT ERROR");
        }
        System.out.println("This "+brandName+" clock with a price of "+price+" have been set to "+timeValue+" "+timeType);

    }
    @Override
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
            case TIMETYPES.SECONDS:
                if (timeValue<0 || timeValue>59) throw new CustomTimeExcept("ERROR: Seconds not in [0,59] range");
                this.seconds+=timeValue;
            default:
                throw new CustomTimeExcept("USER INPUT ERROR");
        }
        System.out.println("This "+brandName+" clock with a price of "+price+" have been advanced by "+timeValue+" "+timeType);
    }
    @Override
    public String GetString() {
        return "Time: "+String.format("%02d:%02d:%02d",this.hours,this.minutes,this.seconds)+ "\n Brand: "+this.GetBrandName()+ "\nPrice: "+this.GetPrice()+"\n";
    }
    @Override
    public String GetTime() {
        return String.format("%02d:%02d:%02d",this.hours,this.minutes,this.seconds);
    }
};