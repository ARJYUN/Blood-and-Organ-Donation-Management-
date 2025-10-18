package com.bodms.model;

public class Hospital {
    private Integer id;
    private String name;
    private String address;
    private String contact;
    private String linkedOrgans; // comma-separated

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getLinkedOrgans() { return linkedOrgans; }
    public void setLinkedOrgans(String linkedOrgans) { this.linkedOrgans = linkedOrgans; }
}
