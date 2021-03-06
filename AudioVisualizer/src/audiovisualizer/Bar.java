package audiovisualizer;

import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadowBuilder;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A simple Item which contains a bar an some of its Options. Also contains the Color mechanism.
 *
 * @author Matthias Stirmayr
 */
public class Bar extends Parent {
    // The wideness of the window
    private int bildschirmbreite = 0;
    //Ammount of bars which are overall used
    private int am = 0;
    //The bar itself
    Rectangle x;
    //The colorindex for Colorchaning
    private int colorIndex = 0;
    //the Starting color which is "needed" to create the bar
    private final Color firstBAR_COLOR = Color.web("c0392b");
    //A colorlist for the Color changing progress
    private ArrayList<Color> colorList = new ArrayList<>();
    
    
    /**
     * Construtor of the Bar
     * @param bild Widness of the Window
     * @param ammount Overall Amount of bars
     */
    public Bar(int bild, int ammount) {
        this.bildschirmbreite = bild;
        this.am = ammount;
        x = new Rectangle((bildschirmbreite / am), 0);
        colorList.add(Color.web("1abc9c"));
        colorList.add(Color.web("f1c40f"));
        colorList.add(Color.web("e67e22"));
        colorList.add(Color.web("c0392b"));
        colorList.add(Color.web("9b59b6"));
        colorList.add(Color.web("3498db"));
        colorList.add(Color.web("2c3e50"));
        colorList.add(Color.web("f39c12"));
        x.setStyle("bottom");
        x.setFill(firstBAR_COLOR);
        getChildren().addAll(x);
        setEffect(DropShadowBuilder.create().blurType(BlurType.TWO_PASS_BOX).radius(10).spread(0.4).color(Color.web("#34495e")).build());
        //setEffect(new MotionBlur(90, 8));
    }
    //Doubleprobperty which is used for Changing the Hight or called value of the bar
    DoubleProperty value = new SimpleDoubleProperty(0) {
        @Override
        protected void invalidated() {
            super.invalidated();
            //double lastBar = get()*bars.length;
            //for(int i=0; i<bars.length;i++) {
            //    bars[i].setVisible(i < lastBar);
            //}
            x.setHeight(value.getValue());
        }
    };
    /**
     * Setter for the Value
     * @param v 
     */
    public void setValue(double v) {
        value.set(v);
    }
    /**
     * Getter for the value. Normaly not used
     * @deprecated 
     * @return 
     */
    public double getValue() {
        return value.get();
    }
    /**
     * Getter for the valueProperty
     * @return Doubleproperty
     */
    public DoubleProperty valueProperty() {
        return value;
    }
    /**
     * Simple method for chaning to nextColor of the Colorlist. At the end of the colorList it will start again at the top.
     */
    public void nextColor() {
        if (colorIndex + 2 == colorList.size()) {
            colorIndex = 0;
        }
        x.setFill(colorList.get(colorIndex));
        colorIndex++;
    }
    /**
     * Is used for Changing the Barswidth when the Window width is changed
     * @param nwBildschirmwith 
     */
    public void adjustwidth(int nwBildschirmwith){
        x.setWidth(nwBildschirmwith/am);
    }
}
