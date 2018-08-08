package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Action;
import model.ActionObject;

import java.util.ArrayList;

public class Cell extends StackPane{
    private int indexI=0;
    private int indexJ=0;
    double radii = 20;
    double R1=105;
    double G1=105;
    double B1=105;
    double R;
    double G;
    double B;
    double O;
    double value;
    public Label label;
    public Color defColor = Color.GAINSBORO;
    public Color agentColor = Color.GREEN;
    public Circle circle;
    Background b1;
    Background b2;
    Background jery;
    Background cheese;
    Pane cover;
    Action action;
    public ArrayList<ActionObject> actions;


    Cell(int a, int b, int dimension){
        indexI=a;
        indexJ=b;
        R=R1/255;
        G=G1/255;
        B=B1/255;
        O=1;
        value = 0;

        circle = null;
        b1 = new Background(new BackgroundFill(defColor,new CornerRadii(0),new Insets(0)));
        b2 = new Background(new BackgroundFill(agentColor,new CornerRadii(0),new Insets(0)));
        jery = new Background(new BackgroundImage(new Image("jerry5.png"),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize(100,130,false,false,true,false)));
        cheese = new Background(new BackgroundImage(new Image("cheesee.png"),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize(100,130,false,false,true,false)));

        setBackground(b1);
        setBorder(new Border(new BorderStroke(Color.TRANSPARENT,BorderStrokeStyle.NONE,new CornerRadii(20),BorderWidths.EMPTY)));
        setMinSize(750/dimension,750/dimension);
        setMaxSize(750/dimension,750/dimension);

        cover = new Pane();
        getChildren().add(cover);

        cover.setBackground(b1);
        cover.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(1),BorderWidths.DEFAULT)));
        cover.setMinSize(750/dimension,750/dimension);
        cover.setMaxSize(750/dimension,750/dimension);

        if((indexI == 0) && (indexJ == 0)){
            cover.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(radii,0,0,0,false),BorderWidths.DEFAULT)));
            b1 = new Background(new BackgroundFill(defColor,new CornerRadii(radii,0,0,0,false),new Insets(0)));
            b2 = new Background(new BackgroundFill(agentColor,new CornerRadii(radii,0,0,0,false),new Insets(0)));
            cover.setBackground(b1);
            setBackground(new Background(new BackgroundFill(defColor,new CornerRadii(radii,0,0,0,false),new Insets(0))));
        }
        if((indexI == dimension-1) && (indexJ == 0)){
            cover.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(0,0,0,20,false),BorderWidths.DEFAULT)));
            b1 = new Background(new BackgroundFill(defColor,new CornerRadii(0,0,0,20,false),new Insets(0)));
            b2 = new Background(new BackgroundFill(agentColor,new CornerRadii(0,0,0,20,false),new Insets(0)));
            cover.setBackground(b1);
            setBackground(new Background(new BackgroundFill(defColor,new CornerRadii(0,0,0,20,false),new Insets(0))));
        }
        if((indexI == dimension-1) && (indexJ == dimension-1)){
            cover.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(0,0,20,0,false),BorderWidths.DEFAULT)));
            b1 = new Background(new BackgroundFill(defColor,new CornerRadii(0,0,20,0,false),new Insets(0)));
            b2 = new Background(new BackgroundFill(agentColor,new CornerRadii(0,0,20,0,false),new Insets(0)));
            cover.setBackground(b1);
            setBackground(new Background(new BackgroundFill(defColor,new CornerRadii(0,0,20,0,false),new Insets(0))));
        }
        if((indexI == 0) && (indexJ == dimension-1)){
            cover.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(0,20,0,0,false),BorderWidths.DEFAULT)));
            b1 = new Background(new BackgroundFill(defColor,new CornerRadii(0,20,0,0,false),new Insets(0)));
            b2 = new Background(new BackgroundFill(agentColor,new CornerRadii(0,20,0,0,false),new Insets(0)));
            cover.setBackground(b1);
            setBackground(new Background(new BackgroundFill(defColor,new CornerRadii(0,20,0,0,false),new Insets(0))));
        }


    }
    public void setCircle(){
        if(circle == null){
            circle= new Circle(value*25);
            circle.setFill(Color.BLACK);
            getChildren().add(circle);
        }
        circle.setRadius(value*20);
    }
    public void setArrowColor(){
        label.setTextFill(Color.GREEN);
    }

    public void setArrow(Action action){
        if(action == Action.RIGHT){
            label = new Label("→");
            this.action = action;
        }
        else if(action == Action.LEFT){
            label = new Label("←");
            this.action = action;
        }
        else if(action == Action.UP){
            label = new Label("↑");
            this.action = action;
        }
        else if(action == Action.DOWN){
            label = new Label("↓");
            this.action = action;
        }
        else {

        }
        label.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0))));
        label.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        getChildren().add(label);
    }

    public void setJerry(){
        cover.setBackground(jery);
    }
    public void setCheese(){
        cover.setBackground(cheese);
    }
    public void setColorDef(){
        cover.setBackground(b1);
    }
    public void setNumericValue(){
        VBox main = new VBox();
        for (ActionObject a: actions) {
            if(a.action == Action.RIGHT){
                label = new Label("R: "+String.valueOf(a.getValue()));
                label.setFont(Font.font("Arial", FontWeight.MEDIUM, 13));
                label.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0))));
                main.getChildren().add(label);
            }
            else if(a.action == Action.LEFT){
                label = new Label("L: "+String.valueOf(a.getValue()));
                label.setFont(Font.font("Arial", FontWeight.MEDIUM, 13));
                label.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0))));
                main.getChildren().add(label);
            }
            else if(a.action == Action.DOWN){
                label = new Label("D: "+String.valueOf(a.getValue()));
                label.setFont(Font.font("Arial", FontWeight.MEDIUM, 13));
                label.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0))));
                main.getChildren().add(label);
            }
            else if(a.action == Action.UP){
                label = new Label("U: "+String.valueOf(a.getValue()));
                label.setFont(Font.font("Arial", FontWeight.MEDIUM, 13));
                label.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0))));
                main.getChildren().add(label);
            }
            else{

            }
        }
        Pane root = new StackPane();
        root.setPadding(new Insets(3,3,3,3));
        main.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0))));
        root.getChildren().add(main);
        getChildren().add(root);
    }
    public int getIndexI(){
        return indexI;
    }
    public int getIndexJ(){
        return indexJ;
    }
    public void setIndexI(int b){
        indexI=b;
    }
    public void setIndexJ(int b){
        indexJ=b;
    }
}
