package graphics;

import java.util.ArrayList;

public abstract class AbstractListenableModel implements ListenableModel{

    private final ArrayList<ModelListener> listeners;

    public AbstractListenableModel(){
        this.listeners = new ArrayList<>();
    }

    @Override
    public void addListener(ModelListener listener){
        listeners.add(listener);
    }

    @Override
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
