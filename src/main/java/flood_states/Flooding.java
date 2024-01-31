package edu.curtin.emergencysim.flood_states;

import edu.curtin.emergencysim.EmergencyState;
import edu.curtin.emergencysim.Controller;

public class Flooding implements EmergencyState{
    private final static int FLOOD_END_TIME = 5;
    private final static double FLOOD_CASUALTY_PROB = 0.5;
    private final static double FLOOD_DAMAGE_PROB = 0.5;
    private boolean teamArrived;
    private int time = 1;
    private Controller c;

    @Override
    public void setController(Controller c){
        this.c = c;

        if(!teamArrived){
            casualty();
            damage();
        }

        if(time == FLOOD_END_TIME){
            c.setState(new NoFlood(0));
            c.sendEnd();
        }
        time++;
    }

    @Override
    public void setEmergencyTeam(boolean teamArrived){
        this.teamArrived = teamArrived;
    }

    public void casualty(){
        double random;

        random = Math.random();
        if(FLOOD_CASUALTY_PROB > random){
            c.sendCasualty();
        }
    }

    public void damage(){
        double random;

        random = Math.random();
        if(FLOOD_DAMAGE_PROB > random){
            c.sendDamage();
        }
    }
}