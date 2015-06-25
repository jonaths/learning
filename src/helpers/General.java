/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.ArrayList;
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
     *
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

    /**
     * Recibe un arraylist y regresa un arreglo 2D para graficarlo
     *
     * @param origin
     * @return
     */
    public static double[][] arrayListTo2DDouble(ArrayList origin) {
        int numEvents = origin.size();

        double[][] m = new double[2][numEvents];
        for (int i = 1; i < numEvents; i++) {
            m[0][i] = i;
            m[1][i] = (double) origin.get(i);
        }

        return m;
    }

    public static ArrayList averageFromHashmap(HashMap<Integer, ArrayList<Double>> data) {
        ArrayList<Double> average = new ArrayList<>();
        int size = data.get(0).size();
        for (int i = 0; i < size; i++) {
            double current = 0.0;
            for (Integer d : data.keySet()) {
                current += data.get(d).get(i);
            }
            average.add(current / size);

        }
        return average;
    }
    
    public static ArrayList<Double> movingAverageFromArrayList(ArrayList<Double>list, int windowSize){
        int size = list.size();
        if(size<windowSize){
            throw new IllegalArgumentException("arraylist shorter than window size");
        }
        ArrayList<Double> output = new ArrayList<>();
        for(int i = 0; i<size; i++){
            if(i == 0){
                output.add(0.0);
            }
            else if(i<windowSize){
                output.add(0.0);
            }
            else{
                double localSum = 0.0;
                for(int j=1; j<windowSize; j++){
                    localSum+=list.get(i-j);
                }
                localSum+=list.get(i);
                output.add(localSum/windowSize);
            }
        }
        
        
        return output;
    }

}
