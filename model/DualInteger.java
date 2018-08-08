package model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by bilal on 26.06.2016.
 */
public class DualInteger {
    private final SimpleIntegerProperty a;
    private final SimpleIntegerProperty b;
    public DualInteger(int a, int b){
        this.a = new SimpleIntegerProperty(a);
        this.b = new SimpleIntegerProperty(b);
    }

    public int getA() {
        return a.get();
    }

    public int getB() {
        return b.get();
    }

    public void setB(int b) {
        this.b.set(b);
    }

    public void setA(int a) {
        this.a.set(a);
    }
}
