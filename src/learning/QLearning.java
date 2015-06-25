/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learning;

/**
 * QLearning Implementación del Algoritmo de QLearning.
 *
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
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
    HashMap<String, HashMap<String, String>> aactionslist = new HashMap<>();
    private final HashMap<Integer, HashMap<String, String>> actionLog;
    private int acounter;

    // Probabilidad de aprender (al usar epsilon greedy)
    double aprobLearn;
    int amode;

    /**
     * Constructor
     *
     * @param alpha
     * @param gamma
     * @param probLearn: la probabilidad de que explote o aprenda
     * @param actionslist: una lista con las posibles acciones
     * @param mode : 
     *      0 - utiliza el valor del constructor siempre, 
     *      1 - explora siempre (ignora valor del constructor), 
     *      2 - explota siempre (ignora valor del constructor), 
     *      3 o cualquier otro - probLearn comienza en uno y disminuye con visitas a estados 
     */
    public QLearning(double alpha, double gamma, double probLearn, HashMap<String, HashMap<String, String>> actionslist, int mode) {
        aalpha = alpha;
        agamma = gamma;
        aqReward = new HashMap<>();
        aqCount = new HashMap<>();
        actionLog = new HashMap<>();
        aactionslist = actionslist;
        aprobLearn = probLearn;
        amode = mode;
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
        double newqval = (1 - getUpdatedAlpha()) * currentqval + getUpdatedAlpha() * (reward + this.agamma * maxqval);
        this.aqReward.get(nowstate).put(action, newqval);
    }
    
    public double getUpdatedAlpha(){
        return this.aalpha;
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

    public HashMap<String,String> chooseAction(String state, String criteria) {
        HashMap<String,String> result = new HashMap<>();

        // Recupera la accion con la maxima recompensa
        if ("max".equals(criteria)) {
            result = getQMaxAction(state);
        }

        // Recupera la accion con maxima recompensa con probabilidad
        // 1 - probLearn. Con probLearn experimenta aleatoriamente
        if ("epsilongreedy".equals(criteria)) {
            double random = Math.random();
            //System.out.println("random: "+random);
            if (random < getUpdatedProbLearn(state)) {
                //System.out.println("random: "+random);
                result = this.getRandomActionFromState(state);
            } else {
                result = getQMaxAction(state);
                //System.out.println("maximize");
            }
        }

        // Colocar aqui cualquier otro criterio para recuperar la accion
        // i.e. epsilon greedy, etc. 
        // Actualiza el log de acciones e incrementa el contador
        this.actionLog.put(this.acounter, result);
        this.acounter += 1;

        // Si el estado no existe regresa "none". Si esto sucede escoge aleatoriamente
        // una accion de entre la lista disponible. 
        if (result.isEmpty()) {
            result = this.getRandomActionFromState(state);
        }

        return result;
    }
    
    public double getUpdatedProbLearn(String state){
         switch (amode) {
            case 0:
                return this.aprobLearn;
            case 1: 
                return 1;
            case 2: 
                return 0;
            default: 
                double value = 1.0/(1.0+this.getQCountsByState(state)/1.0);
                //System.out.println("ZZ: "+state);
                //System.out.println("YY: "+this.getQCountsByState(state));
                //System.out.println("XX: "+value);
                return value;
        }
    }

    /**
     * Recupera una acción aleatoria desde disponible en state
     * @param state
     * @return 
     */
    public HashMap<String,String> getRandomActionFromState(String state) {
        HashMap<String,String> result = new HashMap<>();
        Random random = new Random();
        List<String> keys = new ArrayList<>(this.aactionslist.get(state).keySet());
        String randomKey = keys.get(random.nextInt(keys.size()));
        String value = this.aactionslist.get(state).get(randomKey);
        result.put("nextState", randomKey);
        result.put("action", value);
        return result;
    }

    // Recupera el valor numerico de la accion con el mayor valor q en state
    public HashMap<String,String> getQMaxAction(String state) {
        HashMap<String,String> result = new HashMap<>();
        String maxIndex = "none";
        double maxValue = 0.0;
        
        boolean started = false;
        HashMap<String, Double> rewards = this.aqReward.get(state);
        if (rewards != null) {
            for (String a : rewards.keySet()) {
                if (started == false) {
                    maxIndex = a;
                    maxValue = rewards.get(a);
                    started = true;
                }
                if (rewards.get(a) > maxValue) {
                    maxIndex = a;
                    maxValue = rewards.get(a);
                }
            }
        }
        String nextState = (String) QLearning.getKeyFromValue(aactionslist.get(state), maxIndex);
        result.put("nextState", nextState);
        result.put("action", maxIndex);
        return result;
    }
    
    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
          if (hm.get(o).equals(value)) {
            return o;
          }
        }
        return null;
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
    
    // Actualiza el valor de gamma    
    public void updateMode(int mode) {
        this.amode = mode;
    }

    // Regresa la tabla Q completa
    public HashMap<String, HashMap<String, Double>> getQRewards() {
        return this.aqReward;
    }

    // Regresa el conteo de visitas a cada par estado/accion
    public HashMap<String, HashMap<String, Integer>> getQCounts() {
        return this.aqCount;
    }

    /**
     * Devuelve el numero de veces que se ha visitado state
     * @param state : string con el nombre del estado
     * @return 
     */ 
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
