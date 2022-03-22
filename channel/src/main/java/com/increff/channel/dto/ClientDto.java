package com.increff.channel.dto;

import java.util.List;
import com.increff.channel.client.assureClient.ClientAssureClient;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.ClientData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClientDto
 */
@Service
public class ClientDto {

    @Autowired
    private ClientAssureClient clientAssureClient;

    public List<ClientData> getByQuery(String query) throws ApiException {
        return clientAssureClient.getClientByQuery(query);
    }

    public List<ClientData> getByQueryClient(String query) throws ApiException {
        return clientAssureClient.getClientByQueryClient(query);
    }

    public List<ClientData> getByQueryCustomer(String query) throws ApiException {
        return clientAssureClient.getClientByQueryCustomer(query);
    }
}
