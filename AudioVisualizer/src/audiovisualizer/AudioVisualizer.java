/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiovisualizer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Matthias
 */
public class AudioVisualizer extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
       Label mainLabel = new Label("Audio-Visualizer");
       mainLabel.setId("Heading");
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });
//        
        StackPane root = new StackPane();
        root.getChildren().add(mainLabel);
        
        Scene scene = new Scene(root, 300, 250);
        
        scene.getStylesheets().add("style.css");
        primaryStage.setTitle("AudioVisualizer");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
