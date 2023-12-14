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

    public ClockStore(boolean boolDB){
        if(boolDB){
            clocks = new ArrayList<>();
            servers = new ArrayList<>();}
        else {
            connect();
            clocks = new ArrayList<>();
            servers = new ArrayList<>();
            selectAllDB();
        }
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
    void connect(){
        try{Class.forName("org.sqlite.JDBC");
            con= DriverManager.getConnection("jdbc:sqlite:clocks.db");
            System.out.println("Database connected successfully");
        }
        catch(ClassNotFoundException ex){
            System.out.println("Driver not found");
        }
        catch(SQLException ex){
            System.out.println("Could not connect to database");
        }
    }
    public void AddDB(TimeInterface t){
        if (!ClockBuilder.boolDB) {
            try {
                PreparedStatement statement = con.prepareStatement("INSERT INTO Clock_Table(Brand, Price, Type, Hour, Minute, Second) VALUES (?,?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, t.GetBrandName());
                statement.setInt(2, t.GetPrice());
                statement.setString(3, t.GetType());
                statement.setInt(4, t.GetHour());
                statement.setInt(5, t.GetMin());
                statement.setInt(6, t.GetSec());
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected");
                }
                ResultSet genKeys = statement.getGeneratedKeys();
                if (genKeys.next()) {
                    t.SetID((int)genKeys.getLong(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClockStore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        clocks.add(t);
        CheckID();
        events();
    }
    public void CheckID(){
        int i=0;
        for(TimeInterface ti:clocks){
            ti.SetID(i++);
        }
    }
    public void removeDB(TimeInterface ti){
        if(!ClockBuilder.boolDB){
            try {
            PreparedStatement statement= con.prepareStatement("DELETE FROM Clock_Table WHERE ID = ?");
            statement.setInt(1,ti.GetID());
            statement.executeUpdate();
            }
            catch(SQLException ex){
                Logger.getLogger(ClockStore.class.getName()).log(Level.SEVERE,null,ex);
            }
        }
        clocks.remove(ti);
        CheckID();
        events(); }
    void selectAllDB(){
        clocks.clear();
        try{
            Statement st= con.createStatement();
            ResultSet res = st.executeQuery("select * from Clock_Table");
            while(res.next()){
                if(res.getString("Type").equals("Hr+Min")) {
                    clocks.add(new Clock(res.getString("Brand"),
                    res.getInt("Price"),
                    res.getInt("Hour"),
                    res.getInt("Minute"),
                    res.getInt("ID")));
                }
                if(res.getString("Type").equals("Hr+Min+Sec")) {
                    clocks.add(new AdvancedClock(res.getString("Brand"),
                    res.getInt("Price"),
                    res.getInt("Hour"),
                    res.getInt("Minute"),
                    res.getInt("Second"),
                    res.getInt("ID")));
                }
            }
        }
        catch(SQLException ex){
            Logger.getLogger(ClockStore.class.getName()).log(Level.SEVERE,null,ex);
        }
        events();
    }


}
