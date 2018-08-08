package model;

import java.util.ArrayList;

/**
 * Created by bilal on 22.06.2016.
 */
public class State {
    private int i;
    private int j;
    public ArrayList<ActionObject> actions;

    public State(int i, int y, ArrayList<ActionObject> actions){
        this.i = i;
        this.j = y;
        this.actions = actions;
    }

    public int getI() {
        return i;
    }
    public int getJ() {
        return j;
    }

    public ArrayList<ActionObject> getActions() {
        return actions;
    }

    public boolean addAnAction(ActionObject a){
        if(actions.contains(a)){
            return false;
        }
        else{
            actions.add(a);
            return true;
        }
    }
    public boolean removeAnAction(Action a){
        ActionObject temp = new ActionObject(a,0);
        if(actions.contains(temp)){
            actions.remove(temp);
            return true;
        }
        else{
            return false;
        }
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public boolean equals(Object obj) {
        boolean y = this.getJ() == ((State)obj).getJ();
        boolean x = this.getI() == ((State)obj).getI();
        return x && y ;
    }
}
