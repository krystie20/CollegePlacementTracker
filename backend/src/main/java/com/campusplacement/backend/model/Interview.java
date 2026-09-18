package com.campusplacement.backend.model;
import java.time.LocalDate;
import java.time.LocalTime;

public class Interview {
    private int id, applicationId, roundId;
    private LocalDate interviewDate;
    private LocalTime interviewTime;
    private String mode, location, interviewer, meetingLink, remarks, roundName;

    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getApplicationId(){return applicationId;} public void setApplicationId(int a){this.applicationId=a;}
    public int getRoundId(){return roundId;} public void setRoundId(int r){this.roundId=r;}
    public LocalDate getInterviewDate(){return interviewDate;} public void setInterviewDate(LocalDate d){this.interviewDate=d;}
    public LocalTime getInterviewTime(){return interviewTime;} public void setInterviewTime(LocalTime t){this.interviewTime=t;}
    public String getMode(){return mode;} public void setMode(String m){this.mode=m;}
    public String getLocation(){return location;} public void setLocation(String l){this.location=l;}
    public String getInterviewer(){return interviewer;} public void setInterviewer(String i){this.interviewer=i;}
    public String getMeetingLink(){return meetingLink;} public void setMeetingLink(String m){this.meetingLink=m;}
    public String getRemarks(){return remarks;} public void setRemarks(String r){this.remarks=r;}
    public String getRoundName(){return roundName;} public void setRoundName(String r){this.roundName=r;}
}