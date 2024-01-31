package edu.curtin.emergencysim.chemical_states;

import edu.curtin.emergencysim.EmergencyState;
import edu.curtin.emergencysim.Controller;

public class ChemicalSpill implements EmergencyState{
    private final static int CHEM_CLEANUP_TIME = 5;
    private final static double CHEM_CASUALTY_PROB = 0.1;
    private final static double CHEM_CONTAM_PROB = 0.1;
    private boolean teamArrived;
    private int time = 1;


    @Override
    public void setController(Controller c){
        if(teamArrived){
            if(time == CHEM_CLEANUP_TIME){
                c.setState(new NoChemicalSpill(0));
                c.sendEnd();
            }
            time++;
        }
        else{
            time = 0;
        }
        casualty(c);
        damage(c);
    }

    @Override
    public void setEmergencyTeam(boolean teamArrived){
        this.teamArrived = teamArrived;
    }

    public void casualty(Controller c){
        double random;

        random = Math.random();
        if(CHEM_CASUALTY_PROB > random){
            c.sendCasualty();
        }
    }

    public void damage(Controller c){
        double random;

        random = Math.random();
        if(CHEM_CONTAM_PROB > random){
            c.sendDamage();
        }
    }
}