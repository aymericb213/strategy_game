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
public interface ListenableModel {
    
    ArrayList<ModelListener> listeners = new ArrayList<ModelListener>();
    
    void addListener(ModelListener listener);
    void removeListener(ModelListener listener);
    
}
