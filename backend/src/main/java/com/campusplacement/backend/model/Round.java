package com.campusplacement.backend.model;
import java.time.LocalDate;

public class Round {
    private int id, driveId, roundNumber;
    private String roundName, description, mode, location;
    private LocalDate roundDate;

    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getDriveId(){return driveId;} public void setDriveId(int d){this.driveId=d;}
    public int getRoundNumber(){return roundNumber;} public void setRoundNumber(int r){this.roundNumber=r;}
    public String getRoundName(){return roundName;} public void setRoundName(String r){this.roundName=r;}
    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
    public String getMode(){return mode;} public void setMode(String m){this.mode=m;}
    public String getLocation(){return location;} public void setLocation(String l){this.location=l;}
    public LocalDate getRoundDate(){return roundDate;} public void setRoundDate(LocalDate r){this.roundDate=r;}
}