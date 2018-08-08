package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TopSide extends BorderPane{
    HBox right;
    public Pane cro;

    public TopSide() {
        setMinSize(750+280,30);
        setMaxSize(750+280,30);
        setPadding(new Insets(0,0,5,0));

        Pane min = new littleLine();
        cro = new littleCross();
        right = new HBox(10);
        right.getChildren().addAll(min,cro);
        right.setAlignment(Pos.CENTER_RIGHT);


        Label label = new Label("Qlearning");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        //label.setPadding(new Insets(3,0,0,3));

        //setLeft(label);
        setRight(right);


        cro.setOnMouseClicked(e ->{getScene().getWindow().hide(); System.exit(0);});
        min.setOnMouseClicked(e -> ((Stage)(getScene().getWindow())).setIconified(true));
    }
}