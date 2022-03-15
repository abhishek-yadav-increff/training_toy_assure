package com.increff.toyassure.model;

public class ClientForm {

    private String name;
    private String userEnum;

    public ClientForm(String name, String userEnum) {
        this.name = name;
        this.userEnum = userEnum;
    }

    public ClientForm() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserEnum() {
        return userEnum;
    }

    public void setUserEnum(String userEnum) {
        this.userEnum = userEnum;
    }

    @Override
    public String toString() {
        return "ClientForm [name=" + name + ", userEnum=" + userEnum + "]";
    }

}
