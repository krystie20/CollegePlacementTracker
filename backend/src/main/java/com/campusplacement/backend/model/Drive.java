package com.campusplacement.backend.model;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Drive {
    private int id, companyId, maxBacklogs, passingYear;
    private String role, location, description, allowedDepartments, status, companyName;
    private BigDecimal packageLpa, minCgpa;
    private LocalDate applicationDeadline;

    public Drive() {}

    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getCompanyId(){return companyId;} public void setCompanyId(int c){this.companyId=c;}
    public String getRole(){return role;} public void setRole(String r){this.role=r;}
    public BigDecimal getPackageLpa(){return packageLpa;} public void setPackageLpa(BigDecimal p){this.packageLpa=p;}
    public String getLocation(){return location;} public void setLocation(String l){this.location=l;}
    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
    public BigDecimal getMinCgpa(){return minCgpa;} public void setMinCgpa(BigDecimal m){this.minCgpa=m;}
    public int getMaxBacklogs(){return maxBacklogs;} public void setMaxBacklogs(int m){this.maxBacklogs=m;}
    public String getAllowedDepartments(){return allowedDepartments;} public void setAllowedDepartments(String a){this.allowedDepartments=a;}
    public int getPassingYear(){return passingYear;} public void setPassingYear(int p){this.passingYear=p;}
    public LocalDate getApplicationDeadline(){return applicationDeadline;} public void setApplicationDeadline(LocalDate a){this.applicationDeadline=a;}
    public String getStatus(){return status;} public void setStatus(String s){this.status=s;}
    public String getCompanyName(){return companyName;} public void setCompanyName(String c){this.companyName=c;}
}