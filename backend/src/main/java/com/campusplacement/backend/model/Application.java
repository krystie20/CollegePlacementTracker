package com.campusplacement.backend.model;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Application {
    private int id, studentId, driveId;
    private String status, studentName, role, companyName;
    private Timestamp appliedDate;
    private BigDecimal packageLpa;

    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getStudentId(){return studentId;} public void setStudentId(int s){this.studentId=s;}
    public int getDriveId(){return driveId;} public void setDriveId(int d){this.driveId=d;}
    public String getStatus(){return status;} public void setStatus(String s){this.status=s;}
    public Timestamp getAppliedDate(){return appliedDate;} public void setAppliedDate(Timestamp t){this.appliedDate=t;}
    public String getStudentName(){return studentName;} public void setStudentName(String s){this.studentName=s;}
    public String getRole(){return role;} public void setRole(String r){this.role=r;}
    public String getCompanyName(){return companyName;} public void setCompanyName(String c){this.companyName=c;}
    public BigDecimal getPackageLpa(){return packageLpa;} public void setPackageLpa(BigDecimal p){this.packageLpa=p;}
}