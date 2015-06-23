/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package program;

import action.Action;
import java.util.ArrayList;
import java.util.HashMap;
import learning.QLearning;
import mdp.MDP;
import state.State;
import world.LineWorld;

/**
 *
 * @author jonathan
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Inicio... ");
        LineWorld lineworld = new LineWorld("myline",4);
        lineworld.setup();
        
        lineworld.setOneReward(2, 3, (float) 1.0);
        lineworld.setOneReward(3, 4, (float) 3.0);
        lineworld.setOneReward(4, 4, (float) 1.0);
        
        Object initialState = 1;
        
        AMDP mdp = new AMDP(initialState,lineworld.getStates(),lineworld.getActions());
        
        QLearning q = new QLearning(0.5,0.9,1.0,Program.objectToString(lineworld.getValidMoves()));
        HashMap<String,String> action = q.chooseAction(initialState.toString(), "epsilongreedy");
        mdp.update("right", 10, "2");
        
        System.out.println(action);
        System.out.println(mdp.getCurrentAction());
        System.out.println(mdp.getCurrentState());
        System.out.println(mdp.getCurrentReward());

        
    }
    
    /**
     * Helper: convierte HashMap<Object,ArrayList<Object>> a HashMap<String,ArrayList<String>>
     * @param objects
     * @return 
     */
    public static HashMap<String,HashMap<String,String>> objectToString(HashMap<Object,HashMap<Object,String>> objects){
        HashMap<String,HashMap<String,String>> list = new HashMap<>();
        for(Object o : objects.keySet()){
            HashMap<String,String> a = new HashMap<>();
            for(Object p : objects.get(o).keySet()){
                a.put(p.toString(),objects.get(o).get(p));
            }
            list.put(o.toString(), a);
        }
        return list;
    }
    
}
