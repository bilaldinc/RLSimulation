package view;

import controller.Task;
import controller.Task2;
import controller.Task3;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Agent;
import model.Environment;
import model.State;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by bilal on 25.06.2016.
 */
public class Controller implements Initializable{
    public View view;
    public Environment environment;
    public Agent agent;
    public int dimension;
    public Thread thread1 = null;
    public boolean busy = false;


    @FXML
    public Slider speed;
    @FXML
    public Spinner<Integer> xAxis;
    @FXML
    public Spinner<Integer> yAxis;
    @FXML
    TextField trainInput;
    @FXML
    Button trainButton;
    @FXML
    Button pause;
    @FXML
    Button stop;
    @FXML
    public ProgressIndicator progress;

    @FXML
    public Spinner<Double> alpha;
    @FXML
    public Spinner<Double> Gamma;
    @FXML
    public Spinner<Double> Epsilon;
    @FXML
    public Spinner<Integer> Rewards;
    @FXML
    public Spinner<Integer> InitialQ;
    @FXML
    public Spinner<Integer> Punishments;

    @FXML
    public Spinner<Integer> stddev;
    @FXML
    public Spinner<Integer> kstep;
    @FXML
    public ListView<Integer> list1;
    @FXML
    public ListView<Integer> list2;

    public ObservableList<Integer> history;
    public ObservableList<Integer> history2;


    public Controller(int dimension){
        this.dimension = dimension;

    }

    public void learn(){
        agent.stop =  false;
        agent.updateParamaters();new Thread(new Task2(agent)).start();
    }

    public void remove(){
        environment.removeTerminalState();
        environment.removeStartState();
    }

    public void restart(){
        stop();
        agent.Qtable = new ArrayList<State>();
        agent.updateParamaters();
        environment.refreshTerminalState();
        environment.returnStartState(true);
        history.clear();
        history2.clear();
        history.add(0);
        history2.add(0);
    }

    public void play(){
        agent.updateParamaters();
        agent.stop = false;
        agent.pause = false;
        if(thread1 == null){
            Thread thread1 = new Thread(new Task(agent));
            thread1.start();
        }
    }
    public void stop(){
        agent.stop = true;
    }
    public void pause(){
        agent.pause = true;
    }

    public void train(){
        progress.setProgress(0);
        agent.stop =  false;
        agent.updateParamaters();
        new Thread(new Task3(agent,Integer.parseInt(trainInput.getText()))).start();
        //Platform.runLater(new Task3(agent,Integer.parseInt(trainInput.getText())));
    }
    public void addAgent(){
        environment.setStartState(xAxis.getValue(),yAxis.getValue());
    }
    public void addGoal(){
        environment.setTerminalState(xAxis.getValue(),yAxis.getValue());
    }


    public void setView(View view) {
        this.view = view;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public void showq(){
        view.showQ();
    }
    public void showq2(){
        view.showQ2();
    }
    public void showArrow(){
        view.showArrow();
    }

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        yAxis.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,25,0));
        xAxis.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,25,0));
        alpha.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,1,0.1,0.01));
        Epsilon.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,1,0.01,0.01));
        Gamma.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,1,0.95,0.01));
        Rewards.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,25,1,1));
        Punishments.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,25,0,0));
        InitialQ.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,25,0,1));
        stddev.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50,0,1));
        kstep.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,20,1));
        history = FXCollections.observableArrayList();
        history2 = FXCollections.observableArrayList();
        list1.setItems(history);
        list2.setItems(history2);
        history.add(0);
        history2.add(0);
    }
}
