/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.util.LinkedList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author black
 */
public class AlertBox{
    
    boolean submit, isBi;
    int distance = 0, timeTaken = 0;
    
    public InputValue connectAlert(boolean isConnected){
        
        
        
        Stage connectWindow = new Stage();
        
        connectWindow.initModality(Modality.APPLICATION_MODAL);
        connectWindow.setTitle("Connect Node");
        connectWindow.setWidth(250);
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        
        //error label
        Label errorLabel = new Label("");
        GridPane.setConstraints(errorLabel, 0, 4);
        GridPane.setColumnSpan(errorLabel, 2);
        
        //set distance input
        Label disLabel = new Label("Distance: ");
        TextField disText = new TextField();
        
        GridPane.setConstraints(disLabel, 0, 0);
        GridPane.setConstraints(disText, 1, 0);
        
        //setup timeTaken input
        Label TTLabel = new Label("Time Taken: ");
        TextField TTText = new TextField();
        
        GridPane.setConstraints(TTLabel, 0, 1);
        GridPane.setConstraints(TTText, 1, 1);
                       
        //Sumbit Button and Cancel Button
        Button submitButt = new Button("Submit");
        submitButt.setOnAction((e) -> {
            
            if(disText.getText() != null && !disText.getText().isEmpty() && TTText.getText() != null && !TTText.getText().isEmpty() ){
                
                submit = true;
                distance = Integer.parseInt(disText.getText());
                timeTaken = Integer.parseInt(TTText.getText());
                
                connectWindow.close();
            }
            else{
                //.set
                errorLabel.setText("some value is empty!");
            }
        });
        
        
        Button cancelButt = new Button("Cancel");
        cancelButt.setOnAction((e) -> {

            connectWindow.close();
        });
        
        GridPane.setConstraints(submitButt, 1, 3);
        GridPane.setConstraints(cancelButt, 0, 3);
                      
        grid.getChildren().addAll(disLabel, disText, TTLabel, TTText, submitButt, cancelButt, errorLabel);
        
        //setup RadioButton
        RadioButton uniD = new RadioButton("Unidirectional");
        RadioButton biD = new RadioButton("Bidirectional");
        
        GridPane.setConstraints(uniD, 0, 2);
        GridPane.setConstraints(biD, 1, 2);
        
        ToggleGroup toggle = new ToggleGroup();
        
        uniD.setToggleGroup(toggle);
        biD.setToggleGroup(toggle);
        
        uniD.setSelected(true);
        
        
        if(!isConnected){
            grid.getChildren().addAll(uniD, biD);
        }
                
        Scene sc = new Scene(grid);
        
        connectWindow.setScene(sc);
        connectWindow.showAndWait();
        
        
        return new InputValue(submit, biD.isSelected(), distance, timeTaken);
    }
    
    public static void shortestMessage(LinkedList<Integer> shortestPath, int distance, int timeTaken, int node1, int node2){
        
        Stage outputWindow = new Stage();
        outputWindow.initModality(Modality.APPLICATION_MODAL);
        outputWindow.setTitle("Shortest Path");
        outputWindow.setWidth(250);
        
        VBox grid = new VBox();

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setSpacing(10);
        
        Button ok = new Button("OK");
        ok.setPrefWidth(100);
        ok.setOnMouseClicked((e) -> outputWindow.close());
        
        Label label = new Label();
        
        
        if(shortestPath.isEmpty()){
            
            label.setText("Node " + node1 + " is not connected with " + "Node " + node2);
            grid.getChildren().add(label);
            
        }else{
            
            Label connectionText = new Label("Shortest Path From Node " + shortestPath.getFirst() + " to " + shortestPath.getLast() + ":");
            String shortText = "";
            
           
            for(int i = 0; i < shortestPath.size() - 1; i++){
                
                shortText += shortestPath.get(i) + " > ";
            }
            shortText += shortestPath.getLast();
            
            label.setText(shortText);
            
            Label distanceText = new Label("Distace: " + distance);
            Label TTText = new Label("Time Taken: " + timeTaken);
            
            grid.getChildren().addAll(connectionText, label, distanceText, TTText);
            
        }
        
        grid.getChildren().addAll(ok);
        Scene scene = new Scene(grid);   
        outputWindow.setScene(scene);
        outputWindow.showAndWait();
        
    }
    
}
