package com.campusplacement.backend.model;
import java.math.BigDecimal;

public class Student {
    private int id, userId, batch, backlogs;
    private String registerNo, phone, department, skills, achievements, certifications, projects, name, email;
    private BigDecimal cgpa, tenthPercentage, twelfthPercentage;

    public Student() {}

    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getUserId(){return userId;} public void setUserId(int userId){this.userId=userId;}
    public String getRegisterNo(){return registerNo;} public void setRegisterNo(String r){this.registerNo=r;}
    public String getPhone(){return phone;} public void setPhone(String p){this.phone=p;}
    public String getDepartment(){return department;} public void setDepartment(String d){this.department=d;}
    public int getBatch(){return batch;} public void setBatch(int b){this.batch=b;}
    public BigDecimal getCgpa(){return cgpa;} public void setCgpa(BigDecimal c){this.cgpa=c;}
    public BigDecimal getTenthPercentage(){return tenthPercentage;} public void setTenthPercentage(BigDecimal t){this.tenthPercentage=t;}
    public BigDecimal getTwelfthPercentage(){return twelfthPercentage;} public void setTwelfthPercentage(BigDecimal t){this.twelfthPercentage=t;}
    public int getBacklogs(){return backlogs;} public void setBacklogs(int b){this.backlogs=b;}
    public String getSkills(){return skills;} public void setSkills(String s){this.skills=s;}
    public String getAchievements(){return achievements;} public void setAchievements(String a){this.achievements=a;}
    public String getCertifications(){return certifications;} public void setCertifications(String c){this.certifications=c;}
    public String getProjects(){return projects;} public void setProjects(String p){this.projects=p;}
    public String getName(){return name;} public void setName(String n){this.name=n;}
    public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
}