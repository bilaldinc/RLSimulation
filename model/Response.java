package model;

/**
 * Created by bilal on 20.06.2016.
 */
public class Response {
    private State state;
    private int reward;

    public Response(State state, int reward) {
        this.state = state;
        this.reward = reward;
    }

    public int getReward() {
        return reward;
    }

    public State getState() {
        return state;
    }
}
