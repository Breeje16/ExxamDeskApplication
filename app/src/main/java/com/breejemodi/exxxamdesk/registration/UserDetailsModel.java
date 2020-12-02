package com.breejemodi.exxxamdesk.registration;

public class UserDetailsModel {

    private String username;
    private String college;
    private String year;
    private String whoAreYou;

    public UserDetailsModel(String username, String college, String year, String whoAreYou) {
        this.username = username;
        this.college = college;
        this.year = year;
        this.whoAreYou = whoAreYou;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getWhoAreYou() {
        return whoAreYou;
    }

    public void setWhoAreYou(String whoAreYou) {
        this.whoAreYou = whoAreYou;
    }
}
