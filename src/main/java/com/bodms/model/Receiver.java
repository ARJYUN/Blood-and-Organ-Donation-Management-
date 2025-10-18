package com.bodms.model;

public class Receiver {
    private Integer id;
    private Integer userId;
    private String name;
    private String bloodGroupNeeded;
    private String organNeeded;
    private String location;
    private String contactInfo;
    private boolean approved;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBloodGroupNeeded() { return bloodGroupNeeded; }
    public void setBloodGroupNeeded(String bloodGroupNeeded) { this.bloodGroupNeeded = bloodGroupNeeded; }
    public String getOrganNeeded() { return organNeeded; }
    public void setOrganNeeded(String organNeeded) { this.organNeeded = organNeeded; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
}
