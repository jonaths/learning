/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package program;

import action.Action;
import mdp.MDP;
import state.State;

/**
 *
 * @author jonathan
 */
public class AMDP extends MDP {

    public AMDP(Object initState, State states, Action actions) {
        super(initState, states, actions);
    }

}
