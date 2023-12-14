package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
public class ClockController {
    ClockStore store = ClockBuilder.build();
    TimeInterface t;
    @FXML
    public Label id;
    @FXML
    public Label cloneBrand;
    @FXML
    public Label clonePrice;
    @FXML
    public Label cloneType;
    @FXML
    public Label times;
    @FXML
    public void SetTime(){
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
                    alert.setContentText("Clock set to " + textField.getText() + " " + comboBox.getValue());
                } catch (CustomTimeExcept exc) {
                    alert.setContentText(exc.getMessage());
                }
                alert.showAndWait();
                return null;
            }
            else return null;
        });
        dialog.showAndWait();
        times.setText(t.GetTime());
    }
    public void IncreaseTime(){
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
                    t.IncreaseTime(TIMETYPES.valueOf(comboBox.getValue()), Integer.parseInt(textField.getText()));
                    alert.setContentText("Clock time increased by " + textField.getText() + " " + comboBox.getValue());
                } catch (CustomTimeExcept exc) {
                    alert.setContentText(exc.getMessage());
                }
                alert.showAndWait();
                return null;
            }
            else return null;
        });
        dialog.showAndWait();
        times.setText(t.GetTime());
    }
    public void SetClock(TimeInterface _t){
        this.t = _t;
        cloneBrand.setText((t.GetBrandName()));
        clonePrice.setText(Integer.toString(t.GetPrice()));
        cloneType.setText(t.GetType());
        times.setText(t.GetTime());
        id.setText("ID: "+(t.GetID()));

    }
    public void RemoveClock(){
        store.RemoveClock(t);
    }
}


