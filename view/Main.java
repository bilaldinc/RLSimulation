package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Agent;
import model.Environment;

import java.io.IOException;

public class Main extends Application {
    Environment environment;
    Agent agent;
    View view;
    Controller controller;
    int dimension = 10;

    @Override
    public void start(Stage primaryStage) throws Exception{
        setup(dimension);

        Scene scene = new Scene(view,750+280+20,750+30+20, Color.TRANSPARENT);
        setScreenDrag(scene);
        primaryStage.setTitle("Qlearning");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public void setScreenDrag(Scene scene){
        //-------------actions for dragging the screen
        final WrappedDouble X = new WrappedDouble();
        final WrappedDouble Y = new WrappedDouble();
        scene.setOnMousePressed(e ->{X.x=e.getSceneX();Y.y=e.getSceneY();});
        scene.setOnMouseDragged(e ->{scene.getWindow().setX(e.getScreenX()-X.x); scene.getWindow().setY(e.getScreenY()-Y.y);});
    }

    public void setup(int dimension) throws IOException {

        this.controller = new Controller(dimension);
        this.environment = new Environment(dimension);
        this.agent = new Agent(environment);
        this.view = new View(dimension,controller);

        environment.setView(view);
        environment.setController(controller);
        agent.setView(view);
        agent.setController(controller);
        view.setEnvironment(environment);
        view.setAgent(agent);
        view.setController(controller);

        controller.setView(view);
        controller.setAgent(agent);
        controller.setEnvironment(environment);
    }

    public static void main(String[] args) {
        launch(args);

    }

    class WrappedDouble{
        double x, y;
    }
}
