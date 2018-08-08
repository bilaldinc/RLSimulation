package model;

import controller.runn;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import view.Controller;
import view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by bilal on 22.06.2016.
 */
public class Agent {
    Environment environment;
    AgentState currentState;
    Controller controller;
    public ArrayList<State> Qtable;

    public double alpha;
    public double gamma;
    public double epsilon;
    public double threshold;
    public double temperature;
    public double initialQ;
    int bufferSize;
    long speed;
    public boolean pause = false;
    public  boolean stop = false;
    ConcurrentLinkedQueue<Integer> buffer;
    public ObservableList<Integer> history;
    public View view;

    public Agent(Environment environment){
        this.Qtable = new ArrayList<>();
        this.environment = environment;
        this.bufferSize = 20;
        this.buffer = new ConcurrentLinkedQueue<>();

        this.speed = 500;
        this.initialQ = 0;
        this.alpha = 0.01;
        this.gamma = 0.6;
        this.epsilon = 0.01;

    }

    public double getQ(State s, Action a){
        int temp = Qtable.indexOf(s);
        return  ((AgentState)Qtable.get(temp)).getValueOfTheAction(a);
    }
    public void setQ(State s, Action a, double value){
        int temp = Qtable.indexOf(s);
        ((AgentState)Qtable.get(temp)).setValueOfTheAciton(a,value);
    }
    public AgentState getStateFromQ(State s){
        int index = Qtable.indexOf(s);
        AgentState agentState;
        if(index == -1){
            agentState = new AgentState(s.getI(),s.getJ(),environment.deepClone(s.getActions()),initialQ);
            Qtable.add(agentState);
            return agentState;
        }
        else{
            return (AgentState)Qtable.get(index);
        }
    }

    public void Qlearning(){
        Response response;
        Action action;
        AgentState agentNextState;
        int stepSize = 0;
        int counter = 0;
        double stdDev = 999999;

        while(counter < this.bufferSize || stdDev > this.threshold){
            environment.returnStartState(false);
            currentState = getStateFromQ(environment.getCurrentState());
            while(!environment.getCurrentState().equals(environment.getTerminalState())){
                action = chooseAction(currentState);
                response = takeTheAction(action,false);
                agentNextState = getStateFromQ(response.getState());
                setQ(currentState,action,getQ(currentState,action) + alpha*(response.getReward() +  gamma*getQ(response.getState(),MaxActionFromAgentState(agentNextState)) - getQ(currentState,action)));
                currentState = agentNextState;
                stepSize++;
            }
            if (counter > bufferSize){
                buffer.poll();
                buffer.offer(stepSize);
                stdDev = getStdDev(buffer);
            }
            else {
                buffer.offer(stepSize);
            }
			Platform.runLater(new runn(stepSize,controller,1));
            stepSize = 0;
            counter++;
        }

    }
    public void Qlearning(int iteration){
        Response response;
        Action action;
        AgentState agentNextState;
        int stepSize = 0;
        double counter = 0;
        double stdDev = 999999;

        while(counter < iteration){
            environment.returnStartState(false);
            currentState = getStateFromQ(environment.getCurrentState());
            while(!environment.getCurrentState().equals(environment.getTerminalState())){
                action = chooseAction(currentState);
                response = takeTheAction(action,false);

                agentNextState = getStateFromQ(response.getState());
                setQ(currentState,action,getQ(currentState,action) + alpha*(response.getReward() +  gamma*getQ(response.getState(),MaxActionFromAgentState(agentNextState)) - getQ(currentState,action)));
                currentState = agentNextState;
                stepSize++;
            }
            counter++;
			Platform.runLater(new runn(stepSize,controller,(counter/iteration)));
			stepSize = 0;
        }
    }
    public void QlearningSlow(){
        Response response;
        Action action;
        AgentState agentNextState;
        int stepSize = 0;
        int counter = 0;
        double stdDev = 999999;

        while(true) {
                environment.returnStartState(true);
                environment.refreshTerminalState();
                currentState = getStateFromQ(environment.getCurrentState());
                if(stop){
                    break;
                }
                while (!environment.getCurrentState().equals(environment.getTerminalState())) {
                    action = chooseAction(currentState);
                    if(!stop){
                        response = takeTheAction(action, true);
                    }
                    else{
                        view.removeAgent();
                        response = takeTheAction(action, false);
                    }
                    while (pause){

                    }
                    agentNextState = getStateFromQ(response.getState());
                    setQ(currentState, action, getQ(currentState, action) + alpha * (response.getReward() + gamma * getQ(response.getState(), MaxActionFromAgentState(agentNextState)) - getQ(currentState, action)));
                    currentState = agentNextState;
                    stepSize++;

                    speed = (long) controller.speed.getValue();
                    if(!stop) {

                        try {
                            Thread.sleep(speed);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }

                }
                Platform.runLater(new runn(stepSize,controller,1));
                stepSize = 0;
                counter++;

        }
    }

    public Action chooseAction(State state){
        Action maxAction = MaxActionFromAgentState((AgentState) state);
        ArrayList<ActionObject> actions;

        if(Math.random() > epsilon){
            return maxAction;
        }
        else{
            actions = environment.deepClone(state.getActions());
            actions.remove(new ActionObject(maxAction,0));
            return  actions.get((int)(actions.size()*Math.random())).getAction();
        }
    }

    public Action MaxActionFromAgentState(AgentState state){
        ArrayList<Action> maxList =state.getMaxActions();
        return maxList.get((int)(maxList.size()*Math.random()));
    }

    public Response takeTheAction(Action a,boolean viewChange){
        Response response = null;

        if(a == Action.RIGHT){
            response = environment.right(viewChange);
        }
        else  if(a == Action.LEFT){
            response = environment.left(viewChange);
        }
        else if(a == Action.UP){
            response = environment.up(viewChange);
        }
        else if(a == Action.DOWN){
            response = environment.down(viewChange);
        }
        else{
            System.out.println("error");
        }
        return response;
    }

    public void updateParamaters(){
        this.initialQ = controller.InitialQ.getValue();
        this.alpha = controller.alpha.getValue();
        this.gamma = controller.Gamma.getValue();
        this.epsilon = controller.Epsilon.getValue();
        this.threshold = controller.stddev.getValue();
        this.bufferSize = controller.kstep.getValue();
        environment.setReward(controller.Rewards.getValue());
        environment.setPunishment(controller.Punishments.getValue());
    }

    public double getMean(Collection<Integer> e){
        double sum = 0;
        int size = 0;
        for (Integer a: e) {
            sum += a.doubleValue();
            size++;
        }
        return sum/size;
    }
    public double getVariance(Collection<Integer> e){
        double mean = getMean(e);
        double sum = 0;
        int size=0;
        for (Integer a: e) {
            sum += (mean-a.doubleValue())*(mean-a.doubleValue());
            size++;
        }
        return sum/size;
    }
    public double getStdDev(Collection<Integer> e){
        return Math.sqrt(getVariance(e));
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
