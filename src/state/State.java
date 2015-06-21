/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import java.util.ArrayList;

/**
 *
 * @author jonathan
 */
public class State {
    
    
    private ArrayList states;

    // A state is an Object, states contains all possible states
    public State(ArrayList stateList) {
        this.states = stateList;
    }
    
    // Create then add with addState
    public State(){
        states = new ArrayList<>();
    }

    public ArrayList getStateList() {
        return this.states;
    }
    
    // Verifies if state does not exist in states, then add it
    public void addState(Object state){
        if(!this.states.contains(state)){
            this.states.add(state);
        }
        
    }

}
