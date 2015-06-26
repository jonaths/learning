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
public class LineWorld extends World {

    int size;

    public LineWorld(String name, int size) {
        super(name);
        this.size = size;
    }
       

    @Override
    public void setStates() {
        state = new State();
        for (int x = 1; x < (size + 1); x++) {
            state.addState(x);
        }

    }

    @Override
    public void setActions() {
        action = new Action();
        action.addAction("right");
        action.addAction("left");
    }

    /**
     * Es una línea, los movimientos validos llevan al siguiente estado o al anterior, 
     * salvo por la primera y la última posición. 
     */
    @Override
    public void setValidMoves() {
        for (Integer s : state.getStateList().keySet()) {
            int i = (int) state.getStateList().get(s);
            if (i == 1) {
                HashMap<Integer,String> valid = new HashMap<>();
                valid.put(s + 1, "right");
                validMoves.put(s, valid);
            } else if (i == size) {
                HashMap<Integer,String> valid = new HashMap<>();
                valid.put(s + 0,"right");
                valid.put(s + 0,"left");
                validMoves.put(s, valid);
            } else {
                HashMap<Integer,String> valid = new HashMap<>();
                valid.put(s - 1,"left");
                valid.put(s + 1,"right");
                validMoves.put(s, valid);
            }
        }
    }

    @Override
    public float getReward(Object current, String action) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setTargetStates(ArrayList<Integer> targets) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
