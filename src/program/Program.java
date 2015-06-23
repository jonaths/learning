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
        LineWorld lineworld = new LineWorld("myline", 4);
        lineworld.setup();
        
        lineworld.setOneReward(0, 1, (float) 1.0);
        lineworld.setOneReward(1, 0, (float) 0.0);
        lineworld.setOneReward(1, 2, (float) 1.0);
        lineworld.setOneReward(2, 1, (float) 0.0);
        lineworld.setOneReward(2, 3, (float) 1.0);
        lineworld.setOneReward(3, 2, (float) 0.0);
        lineworld.setOneReward(3, 3, (float) 2.0);
        

        int initialState = 0;

        AMDP mdp = new AMDP(initialState, lineworld.getStates(), lineworld.getActions());
        QLearning q = new QLearning(0.5, 0.9, 1.0, Program.objectToString(lineworld.getValidMoves()));

        int stateNow = initialState;
        HashMap<String, String> operation = q.chooseAction(Integer.toString(stateNow), "epsilongreedy");
        
        System.out.println(operation);

        for (int i = 1; i < 30; i++) {

            String action = operation.get("action");
            int stateNext = Integer.parseInt(operation.get("nextState"));
            System.out.println(lineworld.getRewards().get(stateNow).get(stateNext));
            float reward = lineworld.getRewards().get(stateNow).get(stateNext);
            mdp.update(action, reward, stateNext);
            q.updateQ(Integer.toString(stateNow), action, reward, Integer.toString(stateNext));

            q.printQ();

            stateNow = stateNext;
            operation = q.chooseAction(Integer.toString(stateNow), "epsilongreedy");

        }

    }

    /**
     * Helper: convierte HashMap<Object,ArrayList<Object>> a
     * HashMap<String,ArrayList<String>> @param ob
     *
     * jects
     * @return
     */
    public static HashMap<String, HashMap<String, String>> objectToString(HashMap<Integer, HashMap<Integer, String>> objects) {
        HashMap<String, HashMap<String, String>> list = new HashMap<>();
        for (Object o : objects.keySet()) {
            HashMap<String, String> a = new HashMap<>();
            for (Object p : objects.get(o).keySet()) {
                a.put(p.toString(), objects.get(o).get(p));
            }
            list.put(o.toString(), a);
        }
        return list;
    }

}
