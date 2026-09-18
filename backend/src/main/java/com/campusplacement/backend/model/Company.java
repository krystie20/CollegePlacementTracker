package com.campusplacement.backend.model;

public class Company {
    private int id, userId;
    private String companyName, industry, location, website, description, email;

    public Company() {}

    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getUserId(){return userId;} public void setUserId(int u){this.userId=u;}
    public String getCompanyName(){return companyName;} public void setCompanyName(String c){this.companyName=c;}
    public String getIndustry(){return industry;} public void setIndustry(String i){this.industry=i;}
    public String getLocation(){return location;} public void setLocation(String l){this.location=l;}
    public String getWebsite(){return website;} public void setWebsite(String w){this.website=w;}
    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
    public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
}