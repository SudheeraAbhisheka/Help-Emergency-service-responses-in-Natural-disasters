package edu.curtin.emergencysim;

import edu.curtin.emergencysim.responders.*;
import edu.curtin.emergencysim.chemical_states.NoChemicalSpill;
import edu.curtin.emergencysim.fire_states.NoFire;
import edu.curtin.emergencysim.flood_states.NoFlood;

import java.util.logging.Logger;
import java.util.*;
import java.io.*;

public class App{
    private static Logger logger = Logger.getLogger(App.class.getName());
    private static ResponderComm r;
    private static Subject observable = new Subject();

    public static void main(String[] args)
    {
        String fileName;
        int timer;
        boolean end = false;
        List<String> emergencyTeams;

        r = new ResponderCommImpl();
        fileName = args[0];

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){   
            String line = reader.readLine();
            while(line != null){
                processLine(line);
                line = reader.readLine();
            }
        
            timer = 0;  
            while(!end){
                try{
                    Thread.sleep(1000);
                    System.out.printf("\rtime = %s: ", timer);
                    emergencyTeams = r.poll();
                    observable.notifyObservers(timer);
                    sendTeamStatus(emergencyTeams);  
                    end = getEnd(emergencyTeams);

                    timer++; 
                }
                catch(InterruptedException e){ 
                    throw new AssertionError(e); 
                } 
            }
        }
        catch(InputMismatchException ee){
            String logString = ee.getMessage();
            logger.severe(logString);
        }
        catch(IOException e){
            String logString = "Could not read the file" + e.getMessage();
            logger.severe(logString);
        }
    }

    private static void sendTeamStatus(List<String> emergencyTeams){
        int listLength;
        String key;
        boolean teamStatus;

        listLength = emergencyTeams.size();

        if(listLength > 0){
            if(!emergencyTeams.get(0).equals("end")){
                for(int i = 0; i < listLength; i++){
                    key = genarteKey(emergencyTeams.get(i)); 
                    teamStatus = genarteTeamStatus(emergencyTeams.get(i));

                    observable.notifyTeamStatus(key, teamStatus);
                }
            }
        }
    }

    private static boolean getEnd(List<String> emergencyTeams){
        int listLength;
        boolean end = false;

        listLength = emergencyTeams.size();

        if(listLength > 0){
            if(emergencyTeams.get(0).equals("end")){
                end = true;
            }
        }

        return end;
    }

    private static String genarteKey(String line){
        String[] splitLine;
        splitLine = line.split(" ");
        int lineLength = splitLine.length;
        String locationName;
        String emergencyType;
        String key;

        emergencyType = splitLine[0];
        locationName = splitLine[2];

        for(int i = 3; i < lineLength; i++)
        {
            locationName = locationName + " " + splitLine[i];
        } 
        key = emergencyType + locationName;
        
        return key;
    }

    private static boolean genarteTeamStatus(String line){
        String[] splitLine;
        splitLine = line.split(" ");
        String expression;
        boolean teamStatus = false;

        expression = splitLine[1];

        if(expression.equals("+")){
            teamStatus = true;
        }
        else if(expression.equals("-")){
            teamStatus = false;
        }
        else{
            logger.info("Should either + or -");
        }
        
        return teamStatus;
    }

    private static void processLine(String csvRow) throws IOException
    {
        String[] splitLine;
        splitLine = csvRow.split(" ");
        int lineLength = splitLine.length;

        int time;
        String locationName;
        String emergencyType;
        String key;
        Controller value = null;

        time = Integer.parseInt(splitLine[0]);
        emergencyType = splitLine[1];
        locationName = splitLine[2];

        for(int i = 3; i < lineLength; i++)
        {
            locationName = locationName + " " + splitLine[i];
        } 

        if(emergencyType.equals("fire")){
            value = new Controller(new NoFire(time), emergencyType, locationName, r);   
        }
        else if(emergencyType.equals("flood")){
            value = new Controller(new NoFlood(time), emergencyType, locationName, r);
        }
        else if(emergencyType.equals("chemical")){
            value = new Controller(new NoChemicalSpill(time), emergencyType, locationName, r);
        }
        else{
            logger.severe(emergencyType);
        }
        
        key = emergencyType + locationName;
        observable.addObserver(key, value);
    }
}