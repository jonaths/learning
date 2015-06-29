/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world;

import action.Action;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import state.State;

/**
 *
 * @author jonathan
 */
public class MineWorld extends World{
    
    int size;

    public MineWorld(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public void setStates() {
        System.out.println("set states: "+size);
        state = new State();
        for ( int x = 0; x < size; x++ ) {
            for(int y=0; y < size; y++){
                int[] coordinate = {x,y};
                state.addState(coordinate);
                
            }
            
      }
    }

    @Override
    public void setActions() {
        action = new Action();
        action.addAction("right");
        action.addAction("left");
        action.addAction("up");
        action.addAction("down");
    }

    @Override
    public void setValidMoves() {
        for (Integer s : state.getStateList().keySet()) {
            int x = getCoordinatesFromKey(s)[0];
            int y = getCoordinatesFromKey(s)[1];
            // Elementos del centro
            if( ( (x>0) && (x<(size-1))) && (y>0) && (y<(size-1)) ){
                HashMap<Integer,String> valid = new HashMap<>();
                valid.put(getIndexFromCoordinates(x+1,y+0), "right");
                valid.put(getIndexFromCoordinates(x-1,y+0), "left");
                valid.put(getIndexFromCoordinates(x+0,y+1), "up");
                valid.put(getIndexFromCoordinates(x+0,y-1), "down");
                //System.out.println(s+" "+valid);
                validMoves.put(s, valid);
            }
            // Elementos de la primera columna
            if( ( (x == 0) && (y != 0) && (y != (size-1))) ){
                HashMap<Integer,String> valid = new HashMap<>();
                valid.put(getIndexFromCoordinates(x+1,y+0), "right");
                valid.put(getIndexFromCoordinates(x+0,y+1), "up");
                valid.put(getIndexFromCoordinates(x+0,y-1), "down");
                System.out.println(s+" "+valid);
                validMoves.put(s, valid);
            }
            // Elementos de la última columna
            if( ( (x == (size-1)) && (y != 0) && (y != (size-1))) ){
                HashMap<Integer,String> valid = new HashMap<>();
                valid.put(getIndexFromCoordinates(x-1,y+0), "left");
                valid.put(getIndexFromCoordinates(x+0,y+1), "up");
                valid.put(getIndexFromCoordinates(x+0,y-1), "down");
                //System.out.println(s+" "+valid);
                validMoves.put(s, valid);
            }
            // Elementos de la primera fila
            if( ( (y == 0) && (x != 0) && (x != (size-1))) ){
                HashMap<Integer,String> valid = new HashMap<>();
                valid.put(getIndexFromCoordinates(x+1,y+0), "right");
                valid.put(getIndexFromCoordinates(x-1,y+0), "left");
                valid.put(getIndexFromCoordinates(x+0,y+1), "up");
                //System.out.println(s+" "+valid);
                validMoves.put(s, valid);
            }
            // Elementos de la última columna
            if( ( (y == (size-1)) && (x != 0) && (x != (size-1))) ){
                HashMap<Integer,String> valid = new HashMap<>();
                valid.put(getIndexFromCoordinates(x+1,y+0), "right");
                valid.put(getIndexFromCoordinates(x-1,y+0), "left");
                valid.put(getIndexFromCoordinates(x+0,y-1), "down");
                //System.out.println(s+" "+valid);
                validMoves.put(s, valid);
            }
            // Ahora las esquinas
            // Inferior izquierda
            if( ( (x==0) && (y==0) ) ){
                HashMap<Integer,String> valid = new HashMap<>();
                valid.put(getIndexFromCoordinates(x+1,y+0), "right");
                valid.put(getIndexFromCoordinates(x+0,y+1), "up");
                //System.out.println(s+" "+valid);
                validMoves.put(s, valid);
            }
            // Inferior derecha
            if( ( (x==(size-1)) && (y==0) ) ){
                HashMap<Integer,String> valid = new HashMap<>();
                valid.put(getIndexFromCoordinates(x-1,y+0), "left");
                valid.put(getIndexFromCoordinates(x+0,y+1), "up");
                //System.out.println(s+" "+valid);
                validMoves.put(s, valid);
            }
            // Superior izquierda
            if( ( (y==(size-1)) && (x==0) ) ){
                HashMap<Integer,String> valid = new HashMap<>();
                valid.put(getIndexFromCoordinates(x+1,y+0), "right");
                valid.put(getIndexFromCoordinates(x+0,y-1), "down");
                //System.out.println(s+" "+valid);
                validMoves.put(s, valid);
            }
            // Superior derecha
            if( ( (y==(size-1)) && (x==(size-1)) ) ){
                HashMap<Integer,String> valid = new HashMap<>();
                valid.put(getIndexFromCoordinates(x-1,y+0), "left");
                valid.put(getIndexFromCoordinates(x+0,y-1), "down");    
                //System.out.println(s+" "+valid);
                validMoves.put(s, valid);
            }
        }
    }

    @Override
    public float getReward(Object current, String action) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Regresa las coordenadas usando el indice
     * @param key
     * @return 
     */
    public int[] getCoordinatesFromKey(int key){
        return (int[])state.retrieveByIndex(key);
    }
    
    /**
     * Recupera el indice a partir de las coordenadas
     * @param x
     * @param y
     * @return 
     */
    public int getIndexFromCoordinates(int x, int y){
        int result = -1;
        for(Integer s : state.getStateList().keySet()){
            int rx = getCoordinatesFromKey(s)[0];
            int ry = getCoordinatesFromKey(s)[1];
            // Si las coordenadas coinciden, regresa el indice
            if( (rx==x) && (ry==y) ){
                result = s;
                break;
            }
            // Si el conteo llegó al ultimo elemento de states, no existe la coordenada
            if( s == (size * size)-1){
                throw new IllegalArgumentException("coordinates (x,y): ("+x+","+y+") not in states");
            }
        }
        return result;
    }

    
}
