package com.increff.assure.dto;

import java.util.List;
import com.increff.assure.dto.helper.ClientDtoHelper;
import com.increff.assure.dto.helper.CommonsHelper;
import com.increff.assure.model.ClientData;
import com.increff.assure.model.ClientForm;
import com.increff.assure.pojo.ClientPojo;
import com.increff.assure.service.ApiException;
import com.increff.assure.service.ClientService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClientDto
 */
@Service
public class ClientDto {

    @Autowired
    private ClientService clientService;

    private static final Logger LOGGER = Logger.getLogger(ClientDto.class);

    public void add(ClientForm clientForm) throws ApiException {
        LOGGER.info("In ClientService:add()");
        LOGGER.info("Form data received: " + clientForm.toString());
        ClientPojo clientPojo = ClientDtoHelper.convert(clientForm);
        clientService.add(clientPojo);
    }

    public ClientData get(int id) throws ApiException {
        ClientPojo clientPojo = clientService.get(id);
        return ClientDtoHelper.convert(clientPojo);
    }

    public List<ClientData> getAll() throws ApiException {
        List<ClientPojo> clientPojos = clientService.getAll();
        return ClientDtoHelper.convert(clientPojos);
    }

    public void update(int id, ClientForm f) throws ApiException {
        ClientPojo p = ClientDtoHelper.convert(f);
        clientService.update(id, p);
    }

    public List<ClientData> getByQuery(String query) throws ApiException {
        List<ClientPojo> clientPojos = clientService.getByQuery(CommonsHelper.normalize(query));
        if (clientPojos == null)
            return null;
        return ClientDtoHelper.convert(clientPojos);
    }
}