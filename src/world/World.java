/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world;

import action.Action;
import java.util.ArrayList;
import java.util.HashMap;
import state.State;

/**
 *
 * @author jonathan
 */
public abstract class World {
    
    String name;
    State state;
    Action action;
    HashMap<Object,ArrayList<Object>> validMoves;
    HashMap<Object,Float> rewards;
    

    public World(String name){
        this.name = name;
        validMoves = new HashMap<>();
        rewards = new HashMap<>();
    }
    
    /**
     * Configura el World con estados, acciones y movimientos validos. 
     * Debe llamarse despues de construir el objeto. 
     */
    public void setup(){
        setStates();
        System.out.println("World.setup::states set: "+getStates().getStateList());
        setActions();
        System.out.println("World.setup::actions set: "+getActions().getActionList());
        setValidMoves();
        System.out.println("World.setup::valid moves set: "+getValidMoves());
    }
    
    public abstract void setStates();
    public abstract void setActions();
    
    public State getStates(){
        return this.state;
    }
    
    public Action getActions(){
        return this.action;
    }
    
    /**
     * Debe inicializar validMoves
     */
    public abstract void setValidMoves();
    
    public HashMap<Object,ArrayList<Object>> getValidMoves(){
        return this.validMoves;
    }
    
    public boolean isValidMove(Object current, Object next){
        return (validMoves.containsKey(current) && validMoves.get(current).contains(next));
    }
    
    public abstract float getReward(Object current, String action, Object next);
}
