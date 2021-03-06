/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world;

import action.Action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import state.State;

/**
 *
 * @author jonathan
 */
public abstract class World {
    
    String name;
    State state;
    Action action;
    HashMap<Integer,HashMap<Integer,String>> validMoves;
    HashMap<Integer,HashMap<Integer,Double>> rewards;
    

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
        setRewards(0.0);
        System.out.println("World.setup::default rewards set: "+getRewards());
    }
    
    public abstract void setStates();
    public abstract void setActions();
    
    public State getStates(){
        return this.state;
    }
    
    public Action getActions(){
        return this.action;
    }
    
    public HashMap<Integer,HashMap<Integer,Double>> getRewards(){
        return this.rewards;
    }
    
    /**
     * Debe inicializar validMoves
     */
    public abstract void setValidMoves();
    
    /**
     * Asigna una recompensa r a cada uno de las transiciones validas
     * @param r 
     */
    public void setRewards(Double r){
        for(Integer j : validMoves.keySet()){
            HashMap<Integer,Double> a = new HashMap<>();
            for (Integer k : validMoves.get(j).keySet()){
                a.put(k, r);
            }
            rewards.put(j, a);
        }
    }
    
    /**
     * Valida que la transicion j->k exista y le asigna la recompensa r
     * @param j
     * @param k
     * @param r 
     */
    public void setOneReward(int j, int k, double r){
        if(!rewards.containsKey(j)){
            throw new IllegalArgumentException("state j: "+j+" does not exist");
        }
        if(!rewards.get(j).containsKey(k)){
            throw new IllegalArgumentException("state k: "+k+" does not exist");
        }
        rewards.get(j).put(k, r);
    }
    
    public HashMap<Integer,HashMap<Integer,String>> getValidMoves(){
        return this.validMoves;
    }
    
    public boolean isValidMove(Object current, Object next){
        return (validMoves.containsKey(current) && validMoves.get(current).containsKey(next));
    }
    
        public boolean setTargetStates(ArrayList<Integer> targets, double reward) {
        // Para cada uno de los posibles estados finales
        for(Integer t : targets){
            
            if(!this.stateExists(t)){
                throw new IllegalArgumentException("state : " + t + " not in states");   
            }
            
            Set keys = this.validMoves.get(t).keySet();
            System.out.println(keys.iterator().next());
            
            // Establece que la única transición válida es hacia sí mismo
            HashMap<Integer,String> newMoves = new HashMap<>();
            newMoves.put(t, this.validMoves.get(t).get(keys.iterator().next()));
            this.validMoves.put(t, newMoves);
            
            // Y establece la recompensa por hacer la transición
            HashMap<Integer,Double> newRewards = new HashMap<>();
            newRewards.put(t, reward);
            this.rewards.put(t, newRewards);
            
        }
        return true;
    }
    
    public abstract float getReward(Object current, String action);
    
    public boolean stateExists(Integer state){
        return this.state.getStateList().keySet().contains(state);
    }
}
