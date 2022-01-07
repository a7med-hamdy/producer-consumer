package observer;

import java.util.ArrayList;

public class observable {

    private ArrayList<observer> observers =new ArrayList<observer>();

    public void add(observer  observer){
        observers.add(observer);
    }

    public void notifiy(){
        for (observer obs:observers){
                
        }
    }
}    
