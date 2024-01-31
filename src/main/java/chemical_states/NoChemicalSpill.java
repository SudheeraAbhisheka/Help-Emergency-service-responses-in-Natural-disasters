package edu.curtin.emergencysim.chemical_states;

import edu.curtin.emergencysim.EmergencyState;
import edu.curtin.emergencysim.Controller;

public class NoChemicalSpill implements EmergencyState{
    private int startingTime;

    public NoChemicalSpill(int startingTime){
        this.startingTime = startingTime;
    }

    @Override
    public void setController(Controller c){
        int time = c.getTime();
        
        if(time == startingTime){
            c.setState(new ChemicalSpill());
            c.sendStart();
        }
    }

    @Override
    public void setEmergencyTeam(boolean teamStatus){

    }
}