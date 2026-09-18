package com.campusplacement.backend.model;
import java.math.BigDecimal;

public class RoundResult {
    private int id, applicationId, roundId;
    private BigDecimal marks;
    private String result, remarks, roundName;

    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getApplicationId(){return applicationId;} public void setApplicationId(int a){this.applicationId=a;}
    public int getRoundId(){return roundId;} public void setRoundId(int r){this.roundId=r;}
    public BigDecimal getMarks(){return marks;} public void setMarks(BigDecimal m){this.marks=m;}
    public String getResult(){return result;} public void setResult(String r){this.result=r;}
    public String getRemarks(){return remarks;} public void setRemarks(String r){this.remarks=r;}
    public String getRoundName(){return roundName;} public void setRoundName(String r){this.roundName=r;}
}