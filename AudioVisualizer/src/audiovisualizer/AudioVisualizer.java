/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiovisualizer;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Matthias
 */
public class AudioVisualizer extends Application {
    
    private Desktop desktop = Desktop.getDesktop();
    String path="";
    
    @Override
    public void start(Stage primaryStage) {
        
       Pane mainLabel = new Pane();
       mainLabel.setPrefSize(560,150);
       mainLabel.setId("Heading");
       Label bottomLabel = new Label("by Matthias Stirmayr&Matthias Skopek");
       Button btn = new Button("Titel auswählen");
       final FileChooser fileChooser = new FileChooser();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });
//        
btn.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    configureFileChooser(fileChooser);
                    
                    File file = fileChooser.showOpenDialog(primaryStage);
                    if (file != null) {
                        path=file.getAbsolutePath();
                        System.out.println(path);
                    }
                }
            });
        BorderPane root = new BorderPane();
        root.setTop(mainLabel);
        root.setCenter(btn);
        root.setBottom(bottomLabel);
        
        Scene scene = new Scene(root, 560, 350);
        
        scene.getStylesheets().add("style.css");
        primaryStage.setTitle("AudioVisualizer");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private static void configureFileChooser(
        final FileChooser fileChooser) {      
            fileChooser.setTitle("Choose Music");
            fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
            );                 
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3", "*.mp3")
            );
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
