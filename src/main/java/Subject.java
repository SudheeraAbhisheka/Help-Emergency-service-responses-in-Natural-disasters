package edu.curtin.emergencysim;

import java.util.logging.Logger;
import java.util.*;

public class Subject{
    private static Logger logger = Logger.getLogger(Subject.class.getName());
    private static Map<String, Controller> observers = new HashMap<>();
    
    public void addObserver(String key, Controller value) throws InputMismatchException{
        if(observers.containsKey(key)){
            throw new InputMismatchException("There can only be one " + key);
        }
        else{
            observers.put(key, value); 
        }
    }
    
    public void notifyObservers(int time){
		for(Map.Entry<String, Controller> entry : observers.entrySet()){
            entry.getValue().setTimer(time);
		}
    }

    public void notifyTeamStatus(String key, boolean teamStatus){
        if(observers.get(key) != null){
            Controller e = observers.get(key);
            e.setEmergencyTeam(teamStatus);
        }
        else{
            String logString = "There is no " + key + " to update the team status";
            logger.info(logString);
        }
    }
}
