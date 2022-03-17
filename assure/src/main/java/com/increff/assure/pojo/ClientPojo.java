package com.increff.assure.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import com.increff.assure.enums.UserEnum;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "assure_client")
@Getter
@Setter
public class ClientPojo {

    @Id
    @TableGenerator(name = "client_id", table = "generator_table", pkColumnName = "client_id",
            initialValue = 10000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "client_id")
    private Long id;
    private String name;
    private UserEnum userType;

    public ClientPojo(String name, UserEnum userType) {
        this.name = name;
        this.userType = userType;
    }

    public ClientPojo() {}


}
