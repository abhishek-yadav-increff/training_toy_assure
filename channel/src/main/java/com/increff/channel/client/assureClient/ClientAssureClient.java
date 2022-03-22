package com.increff.channel.client.assureClient;

import java.util.Arrays;
import java.util.List;
import com.increff.commons.model.ClientData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * AssureClient
 */
@Service
public class ClientAssureClient {

    @Autowired
    private RestTemplate restTemplate;



    public List<ClientData> getClientByQuery(String query) {
        ResponseEntity<ClientData[]> response;
        if (query == null || query.isEmpty())
            response = restTemplate.getForEntity(
                    "http://localhost:9000/assure/api/client/search/?type=query",
                    ClientData[].class);
        else
            response = restTemplate.getForEntity(
                    "http://localhost:9000/assure/api/client/search/?term=" + query + "&type=query",
                    ClientData[].class);
        List<ClientData> channelDatas = Arrays.asList(response.getBody());
        return channelDatas;
    }

    public List<ClientData> getClientByQueryClient(String query) {
        ResponseEntity<ClientData[]> response;
        if (query == null || query.isEmpty())
            response = restTemplate.getForEntity(
                    "http://localhost:9000/assure/api/client/client/?type=query",
                    ClientData[].class);
        else
            response = restTemplate.getForEntity(
                    "http://localhost:9000/assure/api/client/client/?term=" + query + "&type=query",
                    ClientData[].class);
        List<ClientData> channelDatas = Arrays.asList(response.getBody());
        return channelDatas;
    }

    public List<ClientData> getClientByQueryCustomer(String query) {
        ResponseEntity<ClientData[]> response;
        if (query == null || query.isEmpty())
            response = restTemplate.getForEntity(
                    "http://localhost:9000/assure/api/client/customer/?type=query",
                    ClientData[].class);
        else
            response = restTemplate
                    .getForEntity("http://localhost:9000/assure/api/client/customer/?term=" + query
                            + "&type=query", ClientData[].class);
        List<ClientData> channelDatas = Arrays.asList(response.getBody());
        return channelDatas;
    }
}
