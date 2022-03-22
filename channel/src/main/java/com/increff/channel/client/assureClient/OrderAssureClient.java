package com.increff.channel.client.assureClient;

import java.util.Arrays;
import java.util.List;
import com.increff.channel.model.OrderData;
import com.increff.channel.model.OrderForm;
import com.increff.commons.model.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * AssureClient
 */
@Service
public class OrderAssureClient {

    @Autowired
    private RestTemplate restTemplate;

    public void addBatchOrder(List<OrderForm> orderForms) {
        restTemplate.postForObject("http://localhost:9000/assure/api/order", orderForms,
                ResponseEntity.class);
    }

    public OrderData getOrder(Long id) {
        ResponseEntity<OrderData> response = restTemplate
                .getForEntity("http://localhost:9000/assure/api/order/" + id, OrderData.class);
        return response.getBody();
    }

    public List<OrderData> getOrders() throws ApiException {
        ResponseEntity<OrderData[]> response = restTemplate
                .getForEntity("http://localhost:9000/assure/api/order", OrderData[].class);
        List<OrderData> orderDatas = Arrays.asList(response.getBody());
        return orderDatas;
    }

}
