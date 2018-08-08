package model;

import java.util.ArrayList;

/**
 * Created by 150113008 on 23.06.2016.
 */
public class AgentState extends State {

    AgentState(int i, int j, ArrayList<ActionObject> actions, double intialQ){
        super(i,j,actions);
    }

    public double getValueOfTheAction(Action a){
        double value = 0;
        for (ActionObject x: actions) {
            if(x.getAction() == a){
                value = x.getValue();
            }
        }
        //actions.get(actions.indexOf(new ActionObject(a,0))).getValue();
        return value;
    }

    public void setValueOfTheAciton(Action a, double value){
        for (ActionObject x: actions) {
            if(x.getAction() == a){
                x.setValue(value);
            }
        }
        //actions.get(actions.indexOf(new ActionObject(a,0))).setValue(value);
    }

    public ArrayList<Action> getMaxActions() {
        double max = -Double.MAX_VALUE;
        ArrayList<Action> maxList = new ArrayList<>();

        for (ActionObject x: actions) {
            if(x.getValue() > max){
                max = x.getValue();
                maxList.clear(); // bu satırı unutma !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                maxList.add(x.getAction());
            }
            else if(x.getValue() == max){
                maxList.add(x.getAction());
            }
            else {

            }
        }
        return maxList;
    }

}
