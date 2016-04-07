/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiovisualizer;

import java.awt.Desktop;
import java.io.File;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Matthias
 */
public class AudioVisualizer extends Application {

    private Desktop desktop = Desktop.getDesktop();
    String path = "";

    @Override
    public void start(Stage primaryStage) {
        /**
         * Button btn = new Button(); btn.setText("Say 'Hello World'");
         * btn.setOnAction(new EventHandler<ActionEvent>() {
         *
         * @Override public void handle(ActionEvent event) {
         * System.out.println("Hello World!"); AudiaVisPlayer p = new
         * AudiaVisPlayer();
         *
         * p.start(); Scene x = new Scene(p.getBox());
         * x.getStylesheets().add("VisCss.css"); primaryStage.setScene(x); } });
         */
        Pane mainLabel = new Pane();
        mainLabel.setPrefSize(560, 150);
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
                            path = file.getAbsolutePath();
                            System.out.println(path);
                            String format = "file:///";
                            path = path.replace("\\", "/");
                            format += path;
                            System.out.println(format);
                            
                            AudiaVisPlayer p = new AudiaVisPlayer();
                            p.start(560,format);
                            Scene x = new Scene(p.getBox());
                            x.widthProperty().addListener(new ChangeListener<Number>() {

                                @Override
                                public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                                    p.bild.set(x.getWidth());
                                    
                                }
                            });
                            
                            
                            x.getStylesheets().add("VisCss.css");
                            primaryStage.setScene(x);
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
