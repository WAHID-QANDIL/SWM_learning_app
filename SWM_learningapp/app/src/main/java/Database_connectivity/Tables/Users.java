package com.example.sqlliteproject.models.Tables;

public class Users {
    private long userId;
    private String userFname;
    private String userLname;
    private int userAge;
    private String userGender;
    private String userEmail;
    private String userPassword;

    public Users(long userId, String userFname, String userLname, int userAge, String userGender, String userEmail, String userPassword) {
        this.userId = userId;
        this.userFname = userFname;
        this.userLname = userLname;
        this.userAge = userAge;
        this.userGender = userGender;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public Users(String userFname, String userLname, int userAge, String userGender, String userEmail, String userPassword) {
        this.userFname = userFname;
        this.userLname = userLname;
        this.userAge = userAge;
        this.userGender = userGender;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long id) {
        this.userId = id;
    }

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int age) {
        this.userAge = age;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + userId +
                ", userFname='" + userFname + '\'' +
                ", userLname='" + userLname + '\'' +
                ", age=" + userAge +
                ", userGender='" + userGender + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
