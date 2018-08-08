package model;

import view.Controller;
import view.View;

import java.util.ArrayList;

/**
 * Created by bilal on 22.06.2016.
 */
public class Environment {
    private int dimension;
    private int reward;
    private int punishment;
    private State startState;
    private State terminalState;
    private State currentState;
    private ArrayList<ActionObject> actions;
    private State states[][];
    private int rewards[][];
    private Controller controller;

    public View view;
    public boolean readOnly;

    public Environment(int dimension){
        this.readOnly = false;
        this.dimension = dimension;
        this.reward = 1;
        this.punishment = 0;
        this.actions = new ArrayList<>();
        actions.add(new ActionObject(Action.RIGHT,0));
        actions.add(new ActionObject(Action.LEFT,0));
        actions.add(new ActionObject(Action.DOWN,0));
        actions.add(new ActionObject(Action.UP,0));

        states = new State[dimension][dimension];
        rewards = new int[dimension][dimension];
        for (int i = 0 ; i < dimension ; i++){
            for (int j = 0 ; j < dimension ; j++){
                states[i][j] = new State(i,j,deepClone(actions));
                rewards[i][j] = 0;
            }
        }
        states[0][0].removeAnAction(Action.UP);
        states[0][0].removeAnAction(Action.LEFT);
        states[0][dimension-1].removeAnAction(Action.UP);
        states[0][dimension-1].removeAnAction(Action.RIGHT);
        states[dimension-1][0].removeAnAction(Action.DOWN);
        states[dimension-1][0].removeAnAction(Action.LEFT);
        states[dimension-1][dimension-1].removeAnAction(Action.RIGHT);
        states[dimension-1][dimension-1].removeAnAction(Action.DOWN);
        for(int k = 1 ; k < dimension-1 ; k++){
            states[0][k].removeAnAction(Action.UP);
            states[k][0].removeAnAction(Action.LEFT);
            states[dimension-1][k].removeAnAction(Action.DOWN);
            states[k][dimension-1].removeAnAction(Action.RIGHT);
        }

        startState = states[0][0];
        currentState = startState;

        terminalState = states[dimension-1][dimension-1];
        rewards[terminalState.getI()][terminalState.getJ()] = reward;
    }

    public Response right(boolean viewChange){
        int reward = 0;
        if((terminalState.getJ() == currentState.getJ()+1) && (terminalState.getI() == currentState.getI()) ){
            reward = this.reward;
        }
        if(viewChange){
            view.removeAgent();
            currentState = states[currentState.getI()][currentState.getJ()+1];
            view.addAgent();
        }
        else{
            currentState = states[currentState.getI()][currentState.getJ()+1];
        }
        return new Response(currentState,reward);
    }
    public Response left(boolean viewChange){
        int reward = 0;
        if((terminalState.getJ() == currentState.getJ()-1) && (terminalState.getI() == currentState.getI()) ){
            reward = this.reward;
        }
        if(viewChange){
            view.removeAgent();
            currentState = states[currentState.getI()][currentState.getJ()-1];
            view.addAgent();
        }
        else{
            currentState = states[currentState.getI()][currentState.getJ()-1];
        }
        return new Response(currentState,reward);

    }
    public Response up(boolean viewChange){
        int reward = 0;
        if((terminalState.getJ() == currentState.getJ()) && (terminalState.getI() == currentState.getI()-1) ){
            reward = this.reward;
        }
        if(viewChange){
            view.removeAgent();
            currentState = states[currentState.getI()-1][currentState.getJ()];
            view.addAgent();
        }
        else{
            currentState = states[currentState.getI()-1][currentState.getJ()];
        }
        return new Response(currentState,reward);

    }
    public Response down(boolean viewChange){
        int reward = 0;
        if((terminalState.getJ() == currentState.getJ()) && (terminalState.getI() == currentState.getI()+1) ){
            reward = this.reward;
        }
        if(viewChange){
            view.removeAgent();
            currentState = states[currentState.getI()+1][currentState.getJ()];
            view.addAgent();
        }
        else {
            currentState = states[currentState.getI()+1][currentState.getJ()];
        }
        return new Response(currentState,reward);

    }

    public void setReward(int reward) {
        if(!readOnly){
            this.reward = reward;
            rewards[terminalState.getI()][terminalState.getJ()] = reward;
        }
    }

    public void setPunishment(int punishment) {
        this.punishment = punishment;
    }

    public void setStartState(int i, int j) {
        if(!readOnly && !(terminalState.getI() == i && terminalState.getJ() == j)){
            this.startState = states[i][j];
            returnStartState(true);
        }
    }

    public void removeStartState(){
        view.grid.getCells(startState.getI(),startState.getJ()).setColorDef();
    }

    public void setTerminalState(int i, int j) {
        if(!readOnly && !(startState.getI() == i && startState.getJ() == j)){
            rewards[terminalState.getI()][terminalState.getJ()] = 0;
            this.terminalState = states[i][j];
            rewards[i][j] = this.reward;
            view.grid.getCells(i,j).setCheese();

        }
    }
    public void removeTerminalState(){
        if(!readOnly){
            rewards[terminalState.getI()][terminalState.getJ()] = 0;
            view.grid.getCells(terminalState.getI(),terminalState.getJ()).setColorDef();
        }
    }

    public State getCurrentState() {
        return currentState;
    }

    public State getTerminalState() {
        return terminalState;
    }

    public void returnStartState(boolean viewChange){
        if(viewChange){
            view.removeAgent();
            currentState = startState;
            view.addAgent();
        }
        else{
            currentState = startState;
        }

    }
    public void refreshTerminalState(){
        view.grid.getCells(terminalState.getI(),terminalState.getJ()).setCheese();
    }

    public void setView(View view) {
        this.view = view;
    }

    public State getStartState() {
        return startState;
    }

    public int getDimension() {
        return dimension;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public ArrayList<ActionObject> deepClone(ArrayList<ActionObject> input){
        ArrayList<ActionObject> output = new ArrayList();
        for (ActionObject temp : input) {
            output.add(new ActionObject(temp.getAction(),temp.getValue()));
        }
        return  output;
    }
}
