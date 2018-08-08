package view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
/*this class creates pane that draws little line
 *this is for minimize button
 **/

public class littleLine extends Pane{
    littleLine(){
        setMinSize(25,25);
        setMaxSize(25,25);
        Line line1 = new Line(5,15,20,15);
        line1.setStrokeWidth(5);
        line1.setStroke(Color.BLACK);
        line1.setStrokeLineCap(StrokeLineCap.ROUND);
        setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0),new CornerRadii(0),new Insets(0))));
        getChildren().add(line1);

        //to make clear to user that area is button, change colors when mouse comes upon it
        setOnMouseEntered(e -> {line1.setStroke(Color.GRAY);});
        setOnMouseExited(e -> {line1.setStroke(Color.BLACK);});
    }
}
