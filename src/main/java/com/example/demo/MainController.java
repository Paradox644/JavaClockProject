package com.example.demo;

import java.io.*;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainController implements Server{
    ClockStore store = ClockBuilder.build();
    int k;
    public MainController(){
    store.AddServer(this);
    }
    @FXML
    public Label lb;
    @FXML
    public TextField price;
    @FXML
    public TextField brand;
    @FXML
    public TextField type;
    @FXML
    public Label error;
    @FXML
    public GridPane ClocksPane;
    @FXML
    public BorderPane Pane;
    @FXML
    public void AddClock(){
        if(type.getText().equals("Hr+Min")){store.AddDB(new Clock(brand.getText(),Integer.parseInt(price.getText()),0,0,k));k++;}
        if(type.getText().equals("Hr+Min+Sec")){store.AddDB(new AdvancedClock(brand.getText(),Integer.parseInt(price.getText()),0,0,0,k));k++;}
    }
    @FXML
    public void SaveBin(){
        FileChooser fc = new FileChooser();
        File file = fc.showSaveDialog(lb.getScene().getWindow());
        if(file!=null){
            lb.setText("File saved to "+file.getAbsolutePath());
            try{
                ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(store);
            }
            catch(IOException ex){
                System.out.println("Write error code 1");
            }
        }
    }
        @FXML
    public void SaveJSON(){
        FileChooser fc= new FileChooser();
        File file = fc.showSaveDialog(lb.getScene().getWindow());
        if(file!=null){
            lb.setText("File saved to "+file.getAbsolutePath());
            try{
                FileWriter oos= new FileWriter(file);
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .registerTypeAdapter(AdvancedClock.class, new AdvancedClockAdapter())
                        .registerTypeAdapter(Clock.class, new ClockAdapter())
                        .create();
                String str= gson.toJson(store);
                oos.write(str);
                oos.close();
            }
            catch(IOException ex){
                System.out.println("Write error code 1");
            }
        }
    }
    @FXML
    public void LoadBin(){
        FileChooser fc= new FileChooser();
        File file=fc.showOpenDialog(lb.getScene().getWindow());
        if(file!=null){
            lb.setText("File loaded from "+file.getAbsolutePath());
            try{
                ObjectInputStream ois= new ObjectInputStream(new FileInputStream(file));
                ClockStore rm = (ClockStore) ois.readObject();
                for (TimeInterface c: rm){store.AddDB(c);}

            }
            catch(IOException ex){
                System.out.println("Write error code 1");
            }
            catch(ClassNotFoundException ex)
            {
                System.out.println("Write error code 2");
            }
        }
    }
    @FXML
    public void LoadJSON(){
        FileChooser fc= new FileChooser();
        File file=fc.showOpenDialog(lb.getScene().getWindow());
        if(file!=null){
            lb.setText("File loaded from "+file.getAbsolutePath());
            try{

                FileReader reader = new FileReader(file);
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .registerTypeAdapter(Clock.class, new ClockAdapter())
                        .registerTypeAdapter(AdvancedClock.class, new AdvancedClockAdapter())
                        .create();
                ClockStore rm = gson.fromJson(reader, ClockStore.class);
                for (TimeInterface c :rm) {store.AddDB(c);}

            }
            catch(IOException ex){System.out.println("Write error code 1");}

        }
    }
    @Override
    public void event(ClockStore _store) {
        ClocksPane.getChildren().clear();
        for (TimeInterface t:store){
            try { ClockController cc= new ClockController();
                FXMLLoader fxmlLoader=new FXMLLoader(ClockController.class.getResource("clocks-view.fxml"));
                fxmlLoader.setController(cc);
                Parent root = fxmlLoader.load();
                cc.SetClock(t);
                ClocksPane.addColumn(0,root);}
            catch (IOException e) {
                throw new RuntimeException(e);
            }}
    }
    public void SetAllClocks(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Dialog");
        ButtonType okButton = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton,ButtonType.CANCEL);
        TextField textField = new TextField();
        textField.setPromptText("Enter time value");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText("Enter type of time to change");
        comboBox.getItems().addAll("HOURS","MINUTES","SECONDS");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(textField,comboBox);
        dialog.getDialogPane().setContent(vBox);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                try {
                    store.SetTimeAll(TIMETYPES.valueOf(comboBox.getValue()), Integer.parseInt(textField.getText()));
                    alert.setContentText("Clocks set to "+textField.getText()+" "+comboBox.getValue());
                } catch (CustomTimeExcept exc) {
                    alert.setContentText(exc.getMessage());
                }
                alert.showAndWait();
                return null;
            }
            else return null;
            });
        dialog.showAndWait();
    }
    public void MostPopularBrand() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Most popular clock brand is "+store.MostPopularBrand());
        alert.showAndWait();
    }
    public void HighestPrice() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Highest price of a clock is "+store.HighestPrice());
        alert.showAndWait();
    }
    public void UniqueBrandsSorted() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Unique brands sorted: "+store.UniqueBrandsSorted());
        alert.showAndWait();
    }

}