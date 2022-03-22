package com.increff.commons.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientForm {

    private String name;
    private String userEnum;

    public ClientForm(String name, String userEnum) {
        this.name = name;
        this.userEnum = userEnum;
    }

    public ClientForm() {}

    @Override
    public String toString() {
        return "ClientForm [name=" + name + ", userEnum=" + userEnum + "]";
    }

}
