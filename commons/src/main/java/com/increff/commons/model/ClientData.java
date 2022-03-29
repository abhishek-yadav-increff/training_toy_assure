package com.increff.commons.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientData extends ClientForm {

    private Long id;

    @Override
    public String toString() {
        return "ClientData [id=" + id + ", name=" + getName() + ", userEnum=" + getUserEnum() + "]";
    }

}
