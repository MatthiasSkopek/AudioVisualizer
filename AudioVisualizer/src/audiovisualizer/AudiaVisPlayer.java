/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiovisualizer;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


/*
 *
 * @author Matthias Stirmayr
 */
public class AudiaVisPlayer implements EventHandler<ActionEvent> {

    private boolean isplay = false;
    private Duration duration;
    private MediaPlayer mediaPlayer;
    private int AMOUNT = 100;
    private VUMeter[] vuMeters = new VUMeter[AMOUNT];
    private Double[] lastValues = new Double[AMOUNT];
    private BorderPane box;
    private HBox lines;
    private double Muliply = 5;
    private AudioSpectrumListener spectrumListener;
    private Parent p;

 final MenuItem  exitItem = new MenuItem("Exit"); 
        final MenuItem  openItem = new MenuItem("Open");
        final MenuItem  helpItem = new MenuItem("Help");   

    public DoubleProperty bild;
    public DoubleProperty heightPropertyListener;
    public void start(int Bildschirmbreite, String pathOfData) {
        bild = new SimpleDoubleProperty(AMOUNT) {
            @Override
            protected void invalidated() {
                super.invalidated();
                for (int i = 0; i < AMOUNT; i++) {
                    double temp32 = bild.getValue();
                    int temp23 = (int)temp32;
                    vuMeters[i].adjustwidth(temp23);
                }
            }
        };
        heightPropertyListener = new SimpleDoubleProperty(0){
            @Override
            protected void invalidated() {
                super.invalidated();
                Muliply = heightPropertyListener.getValue()/60;
            }
        };
        //BOX

        lines = new HBox();

        lines.setPrefHeight(300);
        lines.setAlignment(Pos.BOTTOM_CENTER);
        /**
         * SONGS: hello tears techno testsound trap1 bass bounce
         */
        mediaPlayer = new MediaPlayer(new Media(pathOfData));
        for (int i = 0; i < vuMeters.length; i++) {
            vuMeters[i] = new VUMeter(Bildschirmbreite, AMOUNT);
            vuMeters[i].setId("bottom");

        }
        for (int i = 0; i < lastValues.length; i++) {
            lastValues[i] = 0d;
        }
        lines.getChildren().addAll(vuMeters);
        spectrumListener = new AudioSpectrumListener() {
            @Override
            public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
                double avarage = 0;
                boolean change = false;
                for (int i = 0; i < AMOUNT; i++) {
                    //vuMeters[i].setValue((60 + magnitudes[i]) / 60);
                    /**
                     * ORI vuMeters[i].setValue((60 + magnitudes[i]) / 60);
                     */

                    double temp = ((60 + (magnitudes[i])) * Muliply);
                    if (i < 3) {
                        if (lastValues[i] < temp) {
                            vuMeters[i].setValue(temp);
                            if (temp > Muliply*60*0.75) {
                                change = true;
                            }
                        } else {
                            vuMeters[i].setValue(lastValues[i] * 0.8);
                        }

                    } else {

                        if (lastValues[i] < temp) {
                            vuMeters[i].setValue(temp);
                        } else {
                            vuMeters[i].setValue(lastValues[i] * 0.9);
                        }

                    }
                    lastValues[i] = vuMeters[i].getValue();
                }
                if (change) {
                    for (int i = 0; i < AMOUNT; i++) {
                        vuMeters[i].nextColor();
                    }
                }
            }
        };

        mediaPlayer.setAudioSpectrumNumBands(3*AMOUNT);
        mediaPlayer.setAudioSpectrumListener(spectrumListener);
        mediaPlayer.setAudioSpectrumInterval(1d / 60);

        //TOOL
        final Menu fileMenu = new Menu( "File");
        fileMenu.getItems().add(openItem);
        fileMenu.getItems().add(exitItem);
        final Menu helpMenu = new Menu( "Help");
        helpMenu.getItems().add(helpItem);
        final MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(helpMenu);
        menuBar.setId("menuBar");
        Button play = new Button ();
        Button stop = new Button ();
        Label time = new Label();
        //Do siazt a imma ab!!!!
//        ObservableList<String> options = 
//    FXCollections.observableArrayList(
//        "Drop Shadow",
//        "Motion Blur",
//        "Rainbow"
//    );
//final ComboBox comboBox = new ComboBox(options);
        final ToolBar tool = new ToolBar(
                play,stop,time
        );
        tool.autosize();
        tool.setId("toolbar");
        play.setId("play");
        play.setPrefWidth(40);
        play.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (isplay) {
                    
                    play.setId("play");
                    mediaPlayer.pause();
                    isplay = false;
                } else {
                    play.setId("pause");
                    mediaPlayer.play();
                    isplay = true;
                }

            }
        });

        stop.setId("stop");
        
        stop.setPrefWidth(25);
        stop.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                mediaPlayer.stop();
                isplay = false;
                time.setText(fTime(new Duration(0d), mediaPlayer.getMedia().getDuration()));     
                play.setId("play");

                play.setId("play");
            }
        });

        //time.textProperty().bind(mediaPlayer.currentTimeProperty().asString());
        mediaPlayer.currentTimeProperty().addListener((Observable ov) -> {
            Duration currentTime = mediaPlayer.getCurrentTime();
            time.setText(fTime(currentTime, duration));
        });

        mediaPlayer.setOnReady(() -> {
            duration = mediaPlayer.getMedia().getDuration();
            Duration currentTime = mediaPlayer.getCurrentTime();
            time.setText(fTime(currentTime, duration));
        });

        mediaPlayer.setOnEndOfMedia(() -> {
            time.setText(fTime(new Duration(0d), mediaPlayer.getMedia().getDuration()));
        });
        VBox tools = new VBox();
        tools.getChildren().add(menuBar);
        tools.getChildren().add(tool);
        box = new BorderPane();
        box.setTop(tools);
        box.setBottom(lines);
        box.setId("main");
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public BorderPane getBox() {
        return box;
    }

    public void setBox(BorderPane box) {
        this.box = box;
    }

    private static String fTime(Duration passed, Duration dauer) {
        int pasedTime = (int) Math.floor(passed.toSeconds());
        int pasedH = pasedTime / (60 * 60);
        if (pasedH > 0) {
            pasedTime -= pasedH * 60 * 60;
        }
        int elapsedMinutes = pasedTime / 60;
        int elapsedSeconds = pasedTime - pasedH * 60 * 60
                - elapsedMinutes * 60;

        if (dauer.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(dauer.toSeconds());
            int dauerH = intDuration / (60 * 60);
            if (dauerH > 0) {
                intDuration -= dauerH * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - dauerH * 60 * 60
                    - durationMinutes * 60;
            if (dauerH > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        pasedH, elapsedMinutes, elapsedSeconds,
                        dauerH, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (pasedH > 0) {
                return String.format("%d:%02d:%02d", pasedH,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }
    @Override
    public void handle(ActionEvent event) {
        final Object source = event.getSource();
        if(source==exitItem){
        Platform.exit();
        }else if(source == openItem){
            //Mias ma si nu üwalegen wie ma wida zruck kummt^^
        }
        else if(source == helpItem){
            //Do fandad i a POP UP nice
        }
    }
}
