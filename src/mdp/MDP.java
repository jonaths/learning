/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdp;

import action.Action;
import java.util.ArrayList;
import state.State;

/**
 *
 * @author jonathan
 */
public abstract class MDP {

    private final Object initState;
    private final State states;
    private final Action actions;

    private ArrayList<String> actionLog = new ArrayList();
    private ArrayList<Double> rewardLog = new ArrayList();
    private ArrayList<Object> stateLog = new ArrayList();

    private int updateCount;

    public MDP(Object initState, State states, Action actions) {
        this.initState = initState;
        this.states = states;
        this.actions = actions;
        this.updateCount = 0;
    }

    /**
     * Updates MDP: executed execAction and obtained reward, now in state
     * newState. The MDP is updated only if the action and state exists
     *
     * @param execAction
     * @param reward
     * @param newState
     * @return
     */
    public void update(String execAction, double reward, Object newState) {
        actionLog.add(execAction);
        rewardLog.add(reward);
        stateLog.add(newState);
        updateCount++;
    }

    public int getUpdateCount() {
        return this.updateCount;
    }
    
    public ArrayList getRewardLog() {
        return this.rewardLog;
    }
    
    public ArrayList getActionLog() {
        return this.actionLog;
    }

    public String getCurrentAction() {
        return (String) this.actionLog.get(this.updateCount - 1);
    }

    public Object getCurrentState() {
        return this.stateLog.get(this.updateCount - 1);
    }

    public double getCurrentReward() {
        return this.rewardLog.get(this.updateCount - 1);
    }

}
