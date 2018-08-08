package view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Created by bilal on 24.06.2016.
 */
public class RightSide extends Pane {

    public RightSide(int dimension){
        Parent root = null;
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(20),BorderWidths.DEFAULT)));
        setBackground(new Background(new BackgroundFill(Color.GAINSBORO,new CornerRadii(20),new Insets(0))));
        setMinSize(250,750);
        setMaxSize(250,750);
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/controls.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        getChildren().add(root);

        /*

        VBox main = new VBox();
        getChildren().add(main);
            HBox part1 = new HBox();
            main.getChildren().add(part1);
                Button button1 = new Button("Train Agent");
                part1.getChildren().add(button1);


                Spinner spinner1 = new Spinner();
                part1.getChildren().add(spinner1);

                */

    }

}
