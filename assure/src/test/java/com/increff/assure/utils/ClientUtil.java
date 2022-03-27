package com.increff.assure.utils;

import com.increff.commons.model.ClientForm;

/**
 * ClientUtil
 */
public class ClientUtil {

    public static ClientForm getClientForm(String name, String userEnum) {
        ClientForm clientForm = new ClientForm();
        clientForm.setName(name);
        clientForm.setUserEnum(userEnum);
        return clientForm;
    }
}
