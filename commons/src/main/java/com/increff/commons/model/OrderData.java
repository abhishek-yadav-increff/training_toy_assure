package com.increff.commons.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderData extends OrderForm {

    private Long id;
    private String status;

}
