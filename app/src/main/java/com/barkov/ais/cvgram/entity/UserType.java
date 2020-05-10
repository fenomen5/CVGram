package com.barkov.ais.cvgram.entity;

public class UserType {

    public static final int JOBSEEKER_TYPE = 1;
    public static final int EMPLOYER_TYPE = 2;

    private int id;
    private String name;

    public UserType()
    {

    }

    public UserType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
