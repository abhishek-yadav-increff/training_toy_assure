package com.increff.assure.dto.helper;

import java.util.ArrayList;
import java.util.List;
import com.increff.commons.enums.UserEnum;
import com.increff.commons.model.ClientData;
import com.increff.commons.model.ClientForm;
import com.increff.assure.pojo.ClientPojo;

/**
 * ClientDtoHelper
 */
public class ClientDtoHelper {

    public static ClientData convert(ClientPojo clientPojo) {
        ClientData clientData = new ClientData();
        clientData.setId(clientPojo.getId());
        clientData.setName(clientPojo.getName());
        clientData.setUserEnum(clientPojo.getUserType().toString());
        return clientData;
    }

    public static ClientPojo convert(ClientForm clientForm) {
        ClientPojo clientPojo = new ClientPojo();
        if (clientForm.getName() != null && !clientForm.getName().isEmpty())
            clientPojo.setName(CommonsHelper.normalize(clientForm.getName()));
        if (clientForm.getUserEnum() != null)
            clientPojo.setUserType(
                    UserEnum.fromString(CommonsHelper.normalize(clientForm.getUserEnum())));
        return clientPojo;
    }

    public static List<ClientData> convert(List<ClientPojo> clientPojos) {
        List<ClientData> clientDatas = new ArrayList<ClientData>();
        for (ClientPojo clientPojo : clientPojos) {
            clientDatas.add(convert(clientPojo));
        }
        return clientDatas;
    }

}
