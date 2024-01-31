package edu.curtin.emergencysim.flood_states;

import edu.curtin.emergencysim.EmergencyState;
import edu.curtin.emergencysim.Controller;

public class NoFlood implements EmergencyState{
    private int startingTime;

    public NoFlood(int startingTime){
        this.startingTime = startingTime;
    }

    @Override
    public void setController(Controller c){
        int time = c.getTime();
        
        if(time == startingTime){
            c.setState(new Flooding());
            c.sendStart();
        }
    }

    @Override
    public void setEmergencyTeam(boolean teamStatus){

    }
}