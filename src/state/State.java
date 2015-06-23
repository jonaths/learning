/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jonathan
 */
public class State {
    
    
    private final HashMap<Integer,Object> states;

    // A state is an Object, states contains all possible states
    public State(HashMap<Integer,Object> stateList) {
        this.states = stateList;
    }
    
    // Create then add with addState
    public State(){
        states = new HashMap<>();
    }

    public HashMap<Integer,Object> getStateList() {
        return this.states;
    }
    
    // Verifies if state does not exist in states, then add it
    public void addState(Object state){
        if(!this.states.containsValue(state)){
            this.states.put(states.size(), state);
        }
    }
    
    public Object retrieveByIndex(int i){
        return states.get(i);
    }

}
