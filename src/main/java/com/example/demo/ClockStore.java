package com.example.demo;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.lang.Iterable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClockStore implements Iterable<TimeInterface>, Serializable {
    protected ArrayList<TimeInterface> clocks;
    transient ArrayList<Server> servers;
    transient Connection con;

    public ClockStore(){
        this.clocks=new ArrayList<>();
        this.servers=new ArrayList<>();
    }
    public void AddClock(TimeInterface _clock){
        clocks.add(_clock);
        events();
    }
    public void RemoveClock(TimeInterface _clock){
        clocks.remove(_clock);
        events();
    }
    @Override
    public Iterator<TimeInterface> iterator(){
        return clocks.iterator();
    }
    @Override
    public Spliterator<TimeInterface> spliterator(){
        return clocks.spliterator();
    }
    public Integer HighestPrice(){
        ArrayList<Integer> prices = new ArrayList<>();
        for (TimeInterface _clock : clocks) {
            prices.add(_clock.GetPrice());
        }
        int highest = Collections.max(prices);
        for (TimeInterface _clock : clocks) {
            if (_clock.GetPrice() == highest) System.out.println(_clock.GetBrandName()+" watch, price = "+_clock.GetPrice()+" type = "+ _clock.GetType());
        }
        return highest;
    }
    public String MostPopularBrand(){
        String mostPopularBrand = null;
        int popularityCounter = 0;
        ArrayList<String> brands = new ArrayList<>();
        for(TimeInterface _clock : clocks) {
            brands.add(_clock.GetBrandName());
        }
        for (String _brand : brands){
            int amount = Collections.frequency(brands,_brand);
            if (amount>popularityCounter) {
                popularityCounter = amount;
                mostPopularBrand = _brand;
            }
        }
        System.out.print("\nMost popular clock brand is "+mostPopularBrand+"\n");
        return mostPopularBrand;
    }
    public ArrayList<String> UniqueBrandsSorted(){
        ArrayList<String> uniqueBrandsSorted = new ArrayList<>();
        for (TimeInterface _clock : clocks){
            if (!uniqueBrandsSorted.contains(_clock.GetBrandName())) {
                uniqueBrandsSorted.add(_clock.GetBrandName());
            }
        }
        Collections.sort(uniqueBrandsSorted);
        System.out.print("Unique clock brands sorted:\n");
        for (String _brand : uniqueBrandsSorted){
            System.out.println(_brand);
        }
        System.out.println();
        return uniqueBrandsSorted;
    }
    public void SetTimeAll(TIMETYPES timeType, int timeValue) throws CustomTimeExcept{
        if (timeType== TIMETYPES.SECONDS){
                for (TimeInterface _clock : clocks){
                    if (_clock instanceof AdvancedClock){
                        _clock.SetTime(timeType,timeValue);
                    }
                }

        }
        else for (TimeInterface _clock : clocks){
            _clock.SetTime(timeType,timeValue);
        }
        System.out.println("All clocks have been set to "+timeValue+" "+timeType);
        events();
    }
    public void forEach(Consumer<? super TimeInterface> action){
        Iterable.super.forEach(action);
}
    public void AddServer(Server _server){
        servers.add(_server);
    }

    public void events(){
        servers.forEach( (o)->{
            o.event(this);
        });
    }



}
