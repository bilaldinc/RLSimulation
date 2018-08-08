package controller;

import model.Agent;

/**
 * Created by bilal on 25.06.2016.
 */
public class Task3 extends javafx.concurrent.Task {
    Agent agent;
    int iteration;
    public Task3(Agent agent, int iteration){
        this.agent = agent;
        this.iteration = iteration;
    }
    @Override
    public Integer call() {
        agent.Qlearning(iteration);
        return 5;
    }
}
