package edu.curtin.emergencysim.fire_states;

import edu.curtin.emergencysim.EmergencyState;
import edu.curtin.emergencysim.Controller;

public class NoFire implements EmergencyState{
    private int startingTime;

    public NoFire(int startingTime){
        this.startingTime = startingTime;
    }
    
    @Override
    public void setController(Controller c){
        int time = c.getTime();
        if(time == startingTime){
            c.setState(new FireLow());
            c.sendStart();
        }
    }

    @Override
    public void setEmergencyTeam(boolean teamStatus){

    }
}