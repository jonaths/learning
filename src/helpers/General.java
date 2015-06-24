/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helpers;

import java.util.HashMap;

/**
 *
 * @author jonathan
 */
public class General {
    
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
