package edu.curtin.emergencysim.fire_states;

import edu.curtin.emergencysim.EmergencyState;
import edu.curtin.emergencysim.Controller;

public class FireLow implements EmergencyState{
    private final static int FIRE_LOW_TO_HIGH_TIME = 500;
    private final static int FIRE_LOW_CLEANP_TIME = 5;
    private final static double FIRE_LOW_CASUALTY_PROB = 0.16;
    private final static double FIRE_LOW_DAMAGE_PROB = 0.32;
    private boolean teamArrived;
    private int timerOne = 1;
    private int timerTwo = 1;
    private Controller c;

    @Override
    public void setController(Controller c){
        this.c = c;

        casualty();
        damage();

        if(teamArrived){
            if(timerTwo == FIRE_LOW_CLEANP_TIME){
                c.setState(new NoFire(0));
                c.sendEnd();
            }
            timerTwo++;
            timerOne = 0;
        }
        else{
            if(timerOne == FIRE_LOW_TO_HIGH_TIME){
                c.setState(new FireHigh());
                c.sendHigh();
            }
            timerOne++;
            timerTwo = 0;
        }
    }

    @Override
    public void setEmergencyTeam(boolean teamStatus){
        this.teamArrived = teamStatus;
    }

    public void casualty(){
        double random;

        random = Math.random();
        if(FIRE_LOW_CASUALTY_PROB > random){
            c.sendCasualty();
        }
    }

    public void damage(){
        double random;

        random = Math.random();
        if(FIRE_LOW_DAMAGE_PROB > random){
            c.sendDamage();
        }
    }
}