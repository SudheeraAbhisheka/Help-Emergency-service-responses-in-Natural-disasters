package edu.curtin.emergencysim;

import edu.curtin.emergencysim.responders.*;

public class Controller{
    private String emergencyType;
    private ResponderComm r;
    private String location;
    private int timer;
    private EmergencyState state;
    private boolean teamArrived;
    private int countCasualty = 1;
    private int countDamage = 1;
    
    public Controller(EmergencyState initialState, String emergencyType, String location, ResponderComm r){
        this.state = initialState;
        this.emergencyType = emergencyType;
        this.location = location;
        this.r = r;
    }

    public void setTimer(int timer){
        this.timer = timer;
        state.setController(this);
    }

    public void setEmergencyTeam(boolean teamArrived){
        this.teamArrived = teamArrived;
        state.setEmergencyTeam(teamArrived);
    }

    public int getTime(){ 
        return timer; 
    }

    public void setState(EmergencyState state){
        this.state = state;
        state.setEmergencyTeam(teamArrived);
    }

    public void sendStart(){
        r.send(emergencyType + " start " + location);
    }
    public void sendEnd(){
        r.send(emergencyType + " end " + location);
    }
    public void sendHigh(){
        r.send(emergencyType + " high " + location);
    }
    public void sendLow(){
        r.send(emergencyType + " low " + location);
    }
    public void sendCasualty(){
        r.send(emergencyType + " casualty " + countCasualty + " " + location);
        countCasualty++;
    }
    public void sendDamage(){
        r.send(emergencyType + " damage " + countDamage + " " + location);
        countDamage++;
    }
}