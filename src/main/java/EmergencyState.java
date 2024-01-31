package edu.curtin.emergencysim;

public interface EmergencyState{
    void setController(Controller c);
    void setEmergencyTeam(boolean teamArrived);
}