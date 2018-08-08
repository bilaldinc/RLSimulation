package view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by bilal on 22.06.2016.
 */
public class View extends StackPane{
    public Grid grid;
    public Parent rightSide;
    public TopSide topSide;
    public VBox main;

    public Agent agent;
    public Environment environment;
    public Controller controller;

    public View(int dimension, Controller controller) throws IOException {
        main = new VBox(0);
        grid = new Grid(dimension,dimension);

        FXMLLoader loader =new FXMLLoader(getClass().getClassLoader().getResource("view/controls.fxml"));
        loader.setController(controller);
        rightSide = loader.load();

        topSide = new TopSide();
        setBackground(new Background(new BackgroundFill(Color.color(1,1,1,0.51),new CornerRadii(20),new Insets(0))));

        Pane empty = new Pane();
        empty.setMinSize(30,750);
        empty.setMaxSize(30,750);

        HBox center = new HBox(0);
        center.getChildren().add(grid);
        center.getChildren().add(empty);
        center.getChildren().add(rightSide);

        main.getChildren().add(topSide);
        main.getChildren().add(center);

        getChildren().add(main);
        setPadding(new Insets(5,10,10,10));
        setStyle("-fx-focus-color: black ; -fx-faint-focus-color: transparent ;");

    }

    public  void setAgent(Agent agent){
        this.agent = agent;
    }
    public  void setEnvironment(Environment environment){
        this.environment = environment;
    }

    public void addAgent(){
        grid.getCells(environment.getCurrentState().getI(),environment.getCurrentState().getJ()).setJerry();
    }

    public void removeAgent(){
        grid.getCells(environment.getCurrentState().getI(),environment.getCurrentState().getJ()).setColorDef();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    public void showQ(){
        int max = -99;
        double maxvalue = -999999999;
        double minvalue = 999999999;
        double value;

        ArrayList<State> temp = agent.Qtable;
        for (State a: temp) {
            ((AgentState)a).getValueOfTheAction(((AgentState)a).getMaxActions().get(0));
            if(a.getI() > max){
                max = a.getI();
            }
            if(a.getJ() > max){
                max = a.getJ();
            }

        }
        Grid agentGrid = new Grid(max+1,max+1);

        for (State a: agent.Qtable) {
            value = agent.getQ(a,agent.MaxActionFromAgentState((AgentState) a));
            //agentGrid.getCells(a.getI(),a.getJ()).setText(String.valueOf(value));
            agentGrid.getCells(a.getI(),a.getJ()).value = value;
            agentGrid.getCells(a.getI(),a.getJ()).setCircle();
            if(value> maxvalue){
                maxvalue = value;
            }
            if(value< minvalue){
                minvalue = value;
            }
        }
        VBox main = new VBox(5);
        TopSide top = new TopSide();
        top.setMaxSize(750,30);
        top.setMinSize(750,30);
        main.getChildren().add(top);
        main.getChildren().add(agentGrid);
        main.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0))));
        top.cro.setOnMouseClicked(e ->{top.cro.getScene().getWindow().hide();});

        agentGrid.setBackground(new Background(new BackgroundFill(Color.color(1,1,1,0.90),new CornerRadii(20),new Insets(0))));
        Scene scene = new Scene(main,750,780, Color.TRANSPARENT);
        setScreenDrag(scene);
        Stage stage = new Stage();
        stage.setTitle("Qlearning");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

    }
    public void showArrow(){
        int max = -99;
        Action action;

        ArrayList<State> temp = agent.Qtable;
        for (State a: temp) {
            ((AgentState)a).getValueOfTheAction(((AgentState)a).getMaxActions().get(0));
            if(a.getI() > max){
                max = a.getI();
            }
            if(a.getJ() > max){
                max = a.getJ();
            }

        }
        Grid agentGrid = new Grid(max+1,max+1);
        ArrayList<Action> maxList ;
        for (State a: agent.Qtable) {
            if(!a.equals(environment.getTerminalState())){
                maxList =((AgentState) a).getMaxActions();
                //action = agent.MaxActionFromAgentState((AgentState) a);
                if(maxList.size()==1){
                    agentGrid.getCells(a.getI(),a.getJ()).setArrow(maxList.get(0));
                }
            }
        }

        Background b = new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0)));
        for (int i = 0; i<max+1;i++){
            agentGrid.row[i].setBackground(b);
            for (int j = 0; j<max+1;j++){
                agentGrid.getCells(i,j).setBackground(b);
                ((Pane)agentGrid.getCells(i,j).getChildren().get(0)).setBackground(b);
            }
        }
        agentGrid.column.setBackground(b);


        State state = agent.Qtable.get(agent.Qtable.indexOf(environment.getStartState()));
        State tempState = new State(state.getI(),state.getJ(),null);
        int i = environment.getStartState().getI();
        int j = environment.getStartState().getJ();
        int endi = environment.getTerminalState().getI();
        int endj = environment.getTerminalState().getJ();
        while(!(i == endi && j == endj)){
            if(agentGrid.getCells(i,j).label != null){
                agentGrid.getCells(i,j).label.setTextFill(Color.RED);
            }
            action = agent.MaxActionFromAgentState((AgentState) state);
            if(action == Action.RIGHT){
                j++;
                tempState.setJ(tempState.getJ()+1);
            }
            else  if(action == Action.LEFT){
                j--;
                tempState.setJ(tempState.getJ()-1);
            }
            else if(action == Action.UP){
                i--;
                tempState.setI(tempState.getI()-1);
            }
            else if(action == Action.DOWN){
                i++;
                tempState.setI(tempState.getI()+1);
            }
            else{
                System.out.println("error");
            }
            state = agent.Qtable.get(agent.Qtable.indexOf(tempState));
        }

        //environment.getStartState().

        VBox main = new VBox(5);
        TopSide top = new TopSide();
        top.setMaxSize(750,30);
        top.setMinSize(750,30);
        main.getChildren().add(top);
        main.getChildren().add(agentGrid);
        main.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0))));

        top.cro.setOnMouseClicked(e ->{top.cro.getScene().getWindow().hide();});
        agentGrid.setBackground(new Background(new BackgroundFill(Color.color(1,1,1,0.90),new CornerRadii(20),new Insets(0))));
        Scene scene = new Scene(main,750,780, Color.TRANSPARENT);
        setScreenDrag(scene);
        Stage stage = new Stage();
        stage.setTitle("Qlearning");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public void showQ2(){
        int max = -99;
        double maxvalue = -999999999;
        double minvalue = 999999999;
        double value;

        ArrayList<State> temp = agent.Qtable;
        for (State a: temp) {
            ((AgentState)a).getValueOfTheAction(((AgentState)a).getMaxActions().get(0));
            if(a.getI() > max){
                max = a.getI();
            }
            if(a.getJ() > max){
                max = a.getJ();
            }

        }
        Grid agentGrid = new Grid(max+1,max+1);

        for (State a: agent.Qtable) {
            agentGrid.getCells(a.getI(),a.getJ()).actions = ((AgentState)(a)).actions;
            //value = agent.getQ(a,agent.MaxActionFromAgentState((AgentState) a));
            //agentGrid.getCells(a.getI(),a.getJ()).setText(String.valueOf(value));
            agentGrid.getCells(a.getI(),a.getJ()).setNumericValue();
        }
        VBox main = new VBox(5);
        TopSide top = new TopSide();
        top.setMaxSize(750,30);
        top.setMinSize(750,30);
        main.getChildren().add(top);
        main.getChildren().add(agentGrid);
        main.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0))));
        top.cro.setOnMouseClicked(e ->{top.cro.getScene().getWindow().hide();});

        agentGrid.setBackground(new Background(new BackgroundFill(Color.color(1,1,1,0.90),new CornerRadii(20),new Insets(0))));
        Scene scene = new Scene(main,750,780, Color.TRANSPARENT);
        setScreenDrag(scene);
        Stage stage = new Stage();
        stage.setTitle("Qlearning");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

    }

    public void setScreenDrag(Scene scene){
        //-------------actions for dragging the screen
        final WrappedDouble X = new WrappedDouble();
        final WrappedDouble Y = new WrappedDouble();
        scene.setOnMousePressed(e ->{X.x=e.getSceneX();Y.y=e.getSceneY();});
        scene.setOnMouseDragged(e ->{scene.getWindow().setX(e.getScreenX()-X.x); scene.getWindow().setY(e.getScreenY()-Y.y);});
    }


    class WrappedDouble{
        double x, y;
    }


}
