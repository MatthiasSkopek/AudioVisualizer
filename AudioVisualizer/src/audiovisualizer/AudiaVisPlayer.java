/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiovisualizer;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


/*
 *
 * @author Matthias
 */
public class AudiaVisPlayer {

    private boolean isplay = false;
    private Duration duration;
    private MediaPlayer mediaPlayer;
    private VUMeter[] vuMeters = new VUMeter[100];
    private Double[] lastValues = new Double[100];
    private VBox box;
    private HBox lines;
    private AudioSpectrumListener spectrumListener;
    private Parent p;

    public void start(String pathOfData) {
        lines = new HBox();
        lines.setPrefHeight(300);
        /**
         * SONGS: hello tears techno testsound trap1 bass bounce
         */
        mediaPlayer = new MediaPlayer(new Media(pathOfData));
        for (int i = 0; i < vuMeters.length; i++) {
            vuMeters[i] = new VUMeter();
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
                for (int i = 0; i < 100; i++) {
                    //vuMeters[i].setValue((60 + magnitudes[i]) / 60);
                    /**
                     * ORI vuMeters[i].setValue((60 + magnitudes[i]) / 60);
                     */
                   
                    double temp = ((60 + (magnitudes[i])) * 5);
                    if (i < 3) {
                        if (lastValues[i] < temp) {
                            vuMeters[i].setValue(temp);
                            if (temp > 220) {
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
                    for (int i = 0; i < 100; i++) {
                        vuMeters[i].nextColor();
                    }
                }
            }
        };

        mediaPlayer.setAudioSpectrumNumBands(300);
        mediaPlayer.setAudioSpectrumListener(spectrumListener);
        mediaPlayer.setAudioSpectrumInterval(1d / 30d);

        //TOOL
        Button btn = new Button();
        btn.setId("play");
        btn.setPrefWidth(40);
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (isplay) {
                    btn.setId("play");
                    mediaPlayer.pause();
                    isplay = false;
                } else {
                    btn.setId("pause");
                    mediaPlayer.play();
                    isplay = true;
                }

            }
        });

        Button stop = new Button();
        stop.setId("stop");
        Label time = new Label();
        stop.setPrefWidth(25);
        stop.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                mediaPlayer.stop();
                isplay = false;
                time.setText(fTime(new Duration(0d), mediaPlayer.getMedia().getDuration()));
                
                btn.setId("play");
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
        
        HBox toolline = new HBox(btn, stop, time);

        box = new VBox(lines, toolline);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public VBox getBox() {
        return box;
    }

    public void setBox(VBox box) {
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
}