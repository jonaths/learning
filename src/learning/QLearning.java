/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package learning;

/**
 * QLearning
 * Implementación del Algoritmo de QLearning. 
 * 
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class QLearning {

    // Variables Internas
    private HashMap<String, HashMap<String, Double>> aqReward;
    private HashMap<String, HashMap<String, Integer>> aqCount;
    private double aalpha;
    private double agamma;

    // Lista de acciones
    HashMap<String,ArrayList<String>> aactionslist = new HashMap<>();
    private final HashMap<Integer, HashMap<String, String>> actionLog;
    private int acounter;

    // Probabilidad de aprender (al usar epsilon greedy)
    double aprobLearn;

    /**
     * Constructor
     *
     * @param alpha
     * @param gamma
     * @param probLearn: la probabilidad de que explote o aprenda
     * @param actionslist: una lista con las posibles acciones
     */
    public QLearning(double alpha, double gamma, double probLearn, HashMap<String,ArrayList<String>> actionslist) {
        aalpha = alpha;
        agamma = gamma;
        aqReward = new HashMap<String, HashMap<String, Double>>();
        aqCount = new HashMap<String, HashMap<String, Integer>>();
        actionLog = new HashMap<Integer, HashMap<String, String>>();
        aactionslist = actionslist;
        aprobLearn = probLearn;
    }

    public void updateQ(String nowstate, String action, double reward, String nextstate) {
        if (this.aqReward.get(nowstate) == null) {
            // Crea nuevos estados en tabla de valores y de conteo
            this.aqReward.put(nowstate, new HashMap<String, Double>());
            this.aqCount.put(nowstate, new HashMap<String, Integer>());
        }
        if (this.aqReward.get(nowstate).get(action) == null) {
            // Crea nueva accion en el nowstate para valores y conteo
            this.aqReward.get(nowstate).put(action, reward);
            this.aqCount.get(nowstate).put(action, 0);

        }

        // Actualiza contador de visitas
        int count = this.aqCount.get(nowstate).get(action);
        this.aqCount.get(nowstate).put(action, count += 1);

        // Recupera valor actual estado/accion y valor maximo de estado siguiente
        double currentqval = this.getQVal(nowstate, action);
        double maxqval = getQMaxActionVal(nextstate);

        // Calcula nuevo valor de Q y actualiza la tabla
        double newqval = (1 - this.aalpha) * currentqval + this.aalpha * (reward + this.agamma * maxqval);
        this.aqReward.get(nowstate).put(action, newqval);
    }

    // Recupera el valor almacenado en state/action de la tabla Q
    public double getQVal(String state, String action) {
        return this.aqReward.get(state).get(action);
    }

    // Recupera el valor numerico de la accion con el mayor valor q en state
    public double getQMaxActionVal(String state) {
        double max = 0.0;
        boolean started = false;
        HashMap<String, Double> rewards = this.aqReward.get(state);
        if (rewards != null) {
            for (String a : rewards.keySet()) {
                if (started == false) {
                    max = rewards.get(a);
                    started = true;
                }
                if (rewards.get(a) > max) {
                    max = rewards.get(a);
                }
            }
        }
        return max;
    }

    public String chooseAction(String state, String criteria) {
        String action = "none";
        Random myRandomizer = new Random();

        // Recupera la accion con la maxima recompensa
        if ("max".equals(criteria)) {
            action = getQMaxAction(state);

        }

        // Recupera la accion con maxima recompensa con probabilidad
        // 1 - probLearn. Con probLearn experimenta aleatoriamente
        if ("epsilongreedy".equals(criteria)) {
            if (Math.random() < this.aprobLearn) {
                action = this.aactionslist.get(state).get(myRandomizer.nextInt(this.aactionslist.size()));
            } else {
                action = getQMaxAction(state);
            }
        }

        // Colocar aqui cualquier otro criterio para recuperar la accion
        // i.e. epsilon greedy, etc. 
        // Actualiza el log de acciones e incrementa el contador
        HashMap<String, String> entry = new HashMap<String, String>();
        entry.put("State", state);
        entry.put("Action", action);
        this.actionLog.put(this.acounter, entry);
        this.acounter += 1;

        // Si el estado no existe regresa "none". Si esto sucede escoge aleatoriamente
        // una accion de entre la lista disponible. 
        if ("none".equals(action)) {
            action = this.aactionslist.get(state).get(myRandomizer.nextInt(this.aactionslist.size()));
        }

        return action;
    }

    // Recupera el valor numerico de la accion con el mayor valor q en state
    public String getQMaxAction(String state) {
        double max = 0.0;
        String action = "none";
        boolean started = false;
        HashMap<String, Double> rewards = this.aqReward.get(state);
        if (rewards != null) {
            for (String a : rewards.keySet()) {
                if (started == false) {
                    max = rewards.get(a);
                    action = a;
                    started = true;
                }
                if (rewards.get(a) > max) {
                    max = rewards.get(a);
                    action = a;
                }
            }
        }
        return action;
    }

    // Actualiza el valor de alpha    
    public void updateAlpha(double alpha) {
        this.aalpha = alpha;
    }

    // Actualiza el valor de gamma    
    public void updateGamma(double gamma) {
        this.agamma = gamma;
    }
    
    public void updateProbLearn(double probLearn) {
        this.aprobLearn = probLearn;
    }

    // Regresa la tabla Q completa
    public HashMap<String, HashMap<String, Double>> getQRewards() {
        return this.aqReward;
    }

    // Regresa el conteo de visitas a cada par estado/accion
    public HashMap<String, HashMap<String, Integer>> getQCounts() {
        return this.aqCount;
    }

    // Devuelve el numero de veces que se ha visitado state
    public Integer getQCountsByState(String state) {
        HashMap<String, Integer> counts = this.aqCount.get(state);
        int sum = 0;
        if (counts != null) {
            for (String c : counts.keySet()) {
                sum += counts.get(c);
            }
        }
        return sum;
    }

    // Imprime la tabla Q
    public void printQ() {
        System.out.println("\nQ-Table ===========================");
        for (String state : this.aqReward.keySet()) {
            System.out.println(state + " " + this.aqReward.get(state) + " " + getQCountsByState(state));
        }
    }

    public void printActionLog() {
        System.out.println("\nAction Log ========================");
        for (Integer c : this.actionLog.keySet()) {
            System.out.println(c + " " + this.actionLog.get(c));
        }
    }

    // Guarda la tabla Q y el conteo de visitas
    public void saveQLearning(String name) {
        saveToDisk(this.aqReward, name + "_rewards.dat");
        saveToDisk(this.aqCount, name + "_count.dat");
    }
    
    public void readQLearning(String name) {
        this.aqReward = getFromDisk(name + "_rewards.dat");
        this.aqCount = getFromDisk(name + "_count.dat");
    }

    // Guarda un objeto en el disco
    public void saveToDisk(Object obj, String file) {
        try {
            // Serialize data object to a file
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(obj);
            out.close();
        } catch (Exception e) {
        }
    }

    public HashMap getFromDisk(String file) {
        HashMap map = new HashMap<HashMap, HashMap>();
        try {
            ObjectInputStream fis = new ObjectInputStream(new FileInputStream(file));
            map = (HashMap) fis.readObject();
        } catch (Exception e) {
        }
        return map;
    }
}