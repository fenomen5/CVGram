package com.barkov.ais.cvgram.entity;

public class User {

    private String first_name;
    private String email;
    private String phone;
    private int userType;
    private String login;
    private String password;
    private String token;

    public User(String first_name, String email, String phone, int userType, String login, String password) {
        this.first_name = first_name;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "first_name='" + first_name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", userType=" + userType +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
