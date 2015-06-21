/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package program;

import action.Action;
import java.util.ArrayList;
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
        
        AMDP mdp = new AMDP(1,lineworld.getStates(),lineworld.getActions());
        
    }
    
}
