/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.util.ArrayList;

/**
 *
 * @author jonathan
 */
public class Action {
    
    private ArrayList actionList;
    
    public Action(ArrayList actionList) {
        this.actionList = actionList;
    }
    
    public Action(){
        actionList = new ArrayList<>();
    }

    public ArrayList getActionList() {
        return this.actionList;
    }

    public void addAction(String action){
        if(!this.actionList.contains(action)){
            this.actionList.add(action);
        }
        
    }
    
}
