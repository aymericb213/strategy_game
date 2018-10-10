/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import java.util.ArrayList;

/**
 *
 * @author quentindeme
 */
public abstract class AbstractListenableModel implements ListenableModel{
    
    private ArrayList<ModelListener> listeners;
    
    public AbstractListenableModel(){
        this.listeners = new ArrayList<ModelListener>();
    }
    
    @Override
    public void addListener(ModelListener listener){
        listeners.add(listener);
    }
    
    public void removeListener(ModelListener listener){
        listeners.remove(listener);
    }
    
    /**
    * Update models
    */
    public void stateChange() {
        for(ModelListener listener : this.listeners) {
                listener.update(this);
        }
    }
    
}
