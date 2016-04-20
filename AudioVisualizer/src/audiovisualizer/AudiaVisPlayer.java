package audiovisualizer;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
<<<<<<< HEAD
=======
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
>>>>>>> origin/master
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


/*
 * A simple Audio Player with a Visualizing Part that shwos the frequency bands.
 * @author Matthias Stirmayr
 */
public class AudiaVisPlayer {

    //Simple Boolean that nows if the player is playing
    private boolean isplay = false;
    //The duration of the song
    private Duration duration;
    //The main mediaplayer
    private MediaPlayer mediaPlayer;
    //The amount of bars to be shown. Adjustable!
    private int AMOUNT = 100;
    //Array of all the "Bars"
    private Bar[] vuMeters = new Bar[AMOUNT];
    //Array with the PreValues of all bars so that a smoth look can be created
    private Double[] lastValues = new Double[AMOUNT];
    //The Layout pane
    private BorderPane box;
    //The HBox for all of the Bars
    private HBox lines;
    //A Value which is needed to calculted the hight and the value for color changing. Deppends on Window hight
    private double Muliply = 5;
    //The main audiospectrunListener
    private AudioSpectrumListener spectrumListener;
<<<<<<< HEAD
    private Parent p;

    final MenuItem exitItem = new MenuItem("Exit");
    final MenuItem openItem = new MenuItem("Open");

    public DoubleProperty bild;
    public DoubleProperty heightPropertyListener;
=======
    //MenuItem for exiting the program
    final MenuItem exitItem = new MenuItem("Exit");
    //MenuItem for Return to the opening page
    final MenuItem openItem = new MenuItem("Open");
    //MenuItem for the Help
    final MenuItem helpItem = new MenuItem("Help");
    //A doubleproperty which is used for calculation of the width of the bars
    public DoubleProperty bild;
    //A doubleproperty which is used for calculation of the height of the bars
    public DoubleProperty heightPropertyListener;
    //Slider for Time
    private Slider slider;
>>>>>>> origin/master

    /**
     * A Method wich creates the Player with all of its parts. Also the
     * Calculation is in this method.
     *
     * @return Nothing
     * @param Bildschirmbreite the StartWitdh of the window
     * @param pathOfData path to the File which should be palyed
     * @
     */
    public void start(int Bildschirmbreite, String pathOfData) {
        bild = new SimpleDoubleProperty(0) {
            @Override
            protected void invalidated() {
                super.invalidated();
                for (int i = 0; i < AMOUNT; i++) {
                    double temp32 = bild.getValue();
                    int temp23 = (int) temp32;
                    vuMeters[i].adjustwidth(temp23);
                }
            }
        };
<<<<<<< HEAD
        
        heightPropertyListener = new SimpleDoubleProperty(0){
            @Override
            protected void invalidated() {
                super.invalidated();
                Muliply = heightPropertyListener.getValue()/53;
            }
        };

=======
        heightPropertyListener = new SimpleDoubleProperty(0) {
            @Override
            protected void invalidated() {
                super.invalidated();
                Muliply = heightPropertyListener.getValue() / 60;
            }
        };
>>>>>>> origin/master
        //BOX
        lines = new HBox();
        lines.setMinHeight(100);
        lines.setPrefHeight(300);
        lines.setAlignment(Pos.BOTTOM_CENTER);
<<<<<<< HEAD
 
=======

>>>>>>> origin/master
        mediaPlayer = new MediaPlayer(new Media(pathOfData));
        for (int i = 0; i < vuMeters.length; i++) {
            vuMeters[i] = new Bar(Bildschirmbreite, AMOUNT);
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
<<<<<<< HEAD
                    if (i < 2) {
                        if (lastValues[i] < temp) {
                            vuMeters[i].setValue(temp);
                            if (temp > (Muliply*53*0.8)) {
=======
                    if (i < 3) {
                        if (lastValues[i] < temp) {
                            vuMeters[i].setValue(temp);
                            if (temp > Muliply * 60 * 0.75) {
>>>>>>> origin/master
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

<<<<<<< HEAD
        mediaPlayer.setAudioSpectrumNumBands(2 * AMOUNT);
=======
        mediaPlayer.setAudioSpectrumNumBands(3 * AMOUNT);
>>>>>>> origin/master
        mediaPlayer.setAudioSpectrumListener(spectrumListener);
        mediaPlayer.setAudioSpectrumInterval(1d / 15);

        //TOOL
        final Menu fileMenu = new Menu("File");
        fileMenu.getItems().add(openItem);
        fileMenu.getItems().add(exitItem);
<<<<<<< HEAD
        final MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);
        menuBar.setId("menuBar");
        menuBar.setPrefHeight(35);
        Button play = new Button();
        Button stop = new Button();
        Label time = new Label();
        final ToolBar tool = new ToolBar(
                play, stop, time
=======
        final Menu helpMenu = new Menu("Help");
        helpMenu.getItems().add(helpItem);
        final MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(helpMenu);
        menuBar.setId("menuBar");
        Button play = new Button();
        Button stop = new Button();
        Label time = new Label();
        slider = new Slider();
        slider.setPrefWidth(300);
        slider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (slider.isValueChanging()) {
// multiply duration by percentage calculated by slider position
                    if (duration != null) {
                        mediaPlayer.seek(duration.multiply(slider.getValue() / 100.0));
                    }

                }
            }

        });

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {

            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                slider.setValue(mediaPlayer.getCurrentTime().divide(duration).toMillis() * 100.0);
            }
        });
        //Do siazt a imma ab!!!!
//        ObservableList<String> options = 
//    FXCollections.observableArrayList(
//        "Drop Shadow",
//        "Motion Blur",
//        "Rainbow"
//    );
//final ComboBox comboBox = new ComboBox(options);
        final ToolBar tool = new ToolBar(
                play, stop, time, slider
>>>>>>> origin/master
        );
        tool.autosize();
        tool.setId("toolbar");
        tool.setPrefHeight(30);
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
                mediaPlayer.seek(new Duration(0d));
                isplay = false;
                time.setText(fTime(new Duration(0d), mediaPlayer.getMedia().getDuration()));
                play.setId("play");
                slider.setValue(0.0);
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
            mediaPlayer.stop();
            isplay = false;
            time.setText(fTime(new Duration(0d), mediaPlayer.getMedia().getDuration()));
            play.setId("play");
            slider.setValue(0.0);
            
        });

        tool.setMaxWidth(Double.MAX_VALUE);
        menuBar.setMaxWidth(Double.MAX_VALUE);
        box = new BorderPane();
        box.setTop(tool);
        box.setBottom(lines);

        box.setId("main");
    }

    /**
     * getter of Mediaplayer
     *
     * @return meidaplayer
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /**
     * Setter for Mediaplayer
     *
     * @param mediaPlayer
     */
    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    /**
     * Getter for the Layout Box
     *
     * @return Borderpane
     */
    public BorderPane getBox() {
        return box;
    }

    /**
     * Setter for the layoutbox. Normaly should not be used.
     *
     * @param box
     * @deprecated
     */
    public void setBox(BorderPane box) {
        this.box = box;
    }

    /**
     * A Method that creates a nice fromated String out of passed an overall
     * time.
     *
     * @param passed Passed time
     * @param dauer Overall duration of the song
     * @return String
     */
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

<<<<<<< HEAD
}
=======
    /**
     * Handler for the menubuttons.
     *
     * @param event
     */
    @Override
    public void handle(ActionEvent event) {
        final Object source = event.getSource();
        if (source == exitItem) {
            Platform.exit();
        } else if (source == openItem) {
            System.out.println("a");
        } else if (source == helpItem) {
            //Do fandad i a POP UP nice
        }
    }
}
>>>>>>> origin/master
