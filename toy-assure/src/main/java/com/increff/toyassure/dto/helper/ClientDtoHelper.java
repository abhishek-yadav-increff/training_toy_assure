package com.increff.toyassure.dto.helper;

import java.util.ArrayList;
import java.util.List;
import com.increff.toyassure.enums.UserEnum;
import com.increff.toyassure.model.ClientData;
import com.increff.toyassure.model.ClientForm;
import com.increff.toyassure.pojo.ClientPojo;

/**
 * ClientDtoHelper
 */
public class ClientDtoHelper {

    public static ClientData convert(ClientPojo clientPojo) {
        ClientData clientData = new ClientData();
        clientData.setId(clientPojo.getId());
        clientData.setName(clientPojo.getName());
        clientData.setUserEnum(CommonsHelper.normalize(clientPojo.getUsertype().toString()));
        return clientData;
    }

    public static ClientPojo convert(ClientForm clientForm) {
        ClientPojo clientPojo = new ClientPojo();
        clientPojo.setName(CommonsHelper.normalize(clientForm.getName()));
        clientPojo.setUsertype(
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
