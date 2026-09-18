package com.campusplacement.backend.model;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Placement {
    private int id, studentId, companyId, driveId;
    private String role, status, studentName, companyName;
    private BigDecimal packageLpa;
    private LocalDate placementDate;

    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getStudentId(){return studentId;} public void setStudentId(int s){this.studentId=s;}
    public int getCompanyId(){return companyId;} public void setCompanyId(int c){this.companyId=c;}
    public int getDriveId(){return driveId;} public void setDriveId(int d){this.driveId=d;}
    public String getRole(){return role;} public void setRole(String r){this.role=r;}
    public String getStatus(){return status;} public void setStatus(String s){this.status=s;}
    public BigDecimal getPackageLpa(){return packageLpa;} public void setPackageLpa(BigDecimal p){this.packageLpa=p;}
    public LocalDate getPlacementDate(){return placementDate;} public void setPlacementDate(LocalDate p){this.placementDate=p;}
    public String getStudentName(){return studentName;} public void setStudentName(String s){this.studentName=s;}
    public String getCompanyName(){return companyName;} public void setCompanyName(String c){this.companyName=c;}
}