/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program;

import helpers.General;
import helpers.Plots;
import java.util.ArrayList;
import java.util.HashMap;
import learning.QLearning;
import mdp.MDP;
import world.LineWorld;
import world.MineWorld;
import world.World;

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
        MineWorld mineworld = new MineWorld("myline", 4);
        mineworld.setup();
        
        ArrayList<Integer> target = new ArrayList<>();
        target.add(1);
        mineworld.setTargetStates(target);

    }
    
    public static void experiment1(){
        HashMap<Integer,ArrayList<Double>> results = new HashMap<>();
        
        // Banco de experimentos
        for (int i = 0; i < 500; i++) {
            MDP lineworld1 = lineworld1();
            ArrayList rewards1 = lineworld1.getRewardLog();
            results.put(i, rewards1);
        }

        // Calcula el promedio por timeslot de los resultados
        ArrayList<Double> average = General.averageFromHashmap(results);
        
        ArrayList<Double> averageSmooth = General.movingAverageFromArrayList(average, 8);
        
        // Convierte arraylist a arreglo de dobles para graficar
        double[][] d = General.arrayListTo2DDouble(averageSmooth);

        // Crea vector de datos
        ArrayList<double[][]> data = new ArrayList<>();
        data.add(d);

        // Crea vector de nombres
        ArrayList<String> seriesNames = new ArrayList<>();
        seriesNames.add("Nombre");

        // Grafica
        Plots.plotSimpleLineXY(data, seriesNames, "titulo", "eje x", "eje y");
    }

    /**
     * Experimento inicial
     *
     * @return
     */
    public static MDP lineworld1() {
        System.out.println("Inicio... ");
        LineWorld lineworld = new LineWorld("myline", 4);
        lineworld.setup();

        lineworld.setOneReward(0, 1, (float) +2.0);
        lineworld.setOneReward(1, 0, (float) -1.0);
        lineworld.setOneReward(1, 2, (float) +2.0);
        lineworld.setOneReward(2, 1, (float) -1.0);
        lineworld.setOneReward(2, 3, (float) +2.0);
        lineworld.setOneReward(3, 3, (float) +10.0);

        int initialState = 0;

        AMDP mdp = new AMDP(initialState, lineworld.getStates(), lineworld.getActions());
        QLearning q = new QLearning(0.9, 0.5, 1.0, General.objectToString(lineworld.getValidMoves()), 3);

        int stateNow = initialState;
        HashMap<String, String> operation = q.chooseAction(Integer.toString(stateNow), "epsilongreedy");

        for (int i = 1; i <= 1000; i++) {

            String action = operation.get("action");
            int stateNext = Integer.parseInt(operation.get("nextState"));
            double reward = lineworld.getRewards().get(stateNow).get(stateNext);
            mdp.update(action, (float) reward, stateNext);
            q.updateQ(Integer.toString(stateNow), action, reward, Integer.toString(stateNext));

            stateNow = stateNext;
            operation = q.chooseAction(Integer.toString(stateNow), "epsilongreedy");

        }

        return mdp;

    }

}
