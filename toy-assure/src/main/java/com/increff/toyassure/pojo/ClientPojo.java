package com.increff.toyassure.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.increff.toyassure.enums.UserEnum;

@Entity(name = "assure_client")
public class ClientPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private UserEnum userEnum;

    public ClientPojo(String name, UserEnum userEnum) {
        this.name = name;
        this.userEnum = userEnum;
    }

    public ClientPojo() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEnum getUsertype() {
        return userEnum;
    }

    public void setUsertype(UserEnum userEnum) {
        this.userEnum = userEnum;
    }

}
