package edu.curtin.emergencysim.fire_states;

import edu.curtin.emergencysim.EmergencyState;
import edu.curtin.emergencysim.Controller;

public class FireHigh implements EmergencyState{
    private final static int FIRE_HIGH_TO_LOW_TIME = 5;
    private final static double FIRE_HIGH_CASUALTY_PROB = 0.48;
    private final static double FIRE_HIGH_DAMAGE_PROB = 0.64;
    private boolean teamArrived;
    private int timer = 1;
    private Controller c;

    @Override
    public void setController(Controller c){
        this.c = c;

        casualty();
        damage();

        if(teamArrived){
            if(timer == FIRE_HIGH_TO_LOW_TIME){
                c.setState(new FireLow());
                c.sendLow();
            }
            timer++;
        }
        else{
            timer = 0;
        }
    }

    @Override
    public void setEmergencyTeam(boolean teamArrived){
        this.teamArrived = teamArrived;
    }

    public void casualty(){
        double random;

        random = Math.random();
        if(FIRE_HIGH_CASUALTY_PROB > random){
            c.sendCasualty();
        }
    }

    public void damage(){
        double random;

        random = Math.random();
        if(FIRE_HIGH_DAMAGE_PROB > random){
            c.sendDamage();
        }
    }
}