package controller;

import model.Agent;

/**
 * Created by bilal on 25.06.2016.
 */
public class Task implements Runnable {
    Agent agent;
    public Task(Agent agent){
        this.agent = agent;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        agent.QlearningSlow();
    }
}
