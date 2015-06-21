/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import action.Action;
import java.util.ArrayList;
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
        for (Object s : state.getStateList()) {
            int i = (int) s;
            if (i == 1) {
                ArrayList<Object> valid = new ArrayList<>();
                valid.add(i + 1);
                validMoves.put(i, valid);
            } else if (i == size) {
                ArrayList<Object> valid = new ArrayList<>();
                valid.add(i - 1);
                validMoves.put(i, valid);
            } else {
                ArrayList<Object> valid = new ArrayList<>();
                valid.add(i - 1);
                valid.add(i + 1);
                validMoves.put(i, valid);
            }
        }
    }

    @Override
    public float getReward(Object current, String action, Object next) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
