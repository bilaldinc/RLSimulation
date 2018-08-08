package model;

/**
 * Created by bilal on 28.06.2016.
 */
public class ActionObject {
    public Action action;
    public double value;

    public ActionObject(Action action,double value){
        this.action = action;
        this.value = value;
    }

    public Action getAction() {
        return action;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        return action == ((ActionObject)obj).getAction();
    }
}
