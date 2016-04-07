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
 * Simple 20bar meter
 *
 * @author Jasper Potts
 */
public class VUMeter extends Parent {

    private int colorIndex = 0;
    private final Color firstBAR_COLOR = Color.web("#cf0f0f");
    private ArrayList<Color> colorList = new ArrayList<>();
    //private Rectangle[] bars = new Rectangle[20];
    Rectangle x = new Rectangle(10, 0);
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

    public void setValue(double v) {
        value.set(v);
    }

    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public VUMeter() {
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
        //setEffect(DropShadowBuilder.create().blurType(BlurType.TWO_PASS_BOX).radius(10).spread(0.4).color(Color.web("#b10000")).build());
        //setEffect(new MotionBlur(90, 8));
    }

    public void nextColor() {
        if (colorIndex + 2 == colorList.size()) {
            colorIndex = 0;
        }
        x.setFill(colorList.get(colorIndex));
        colorIndex++;
    }
}
