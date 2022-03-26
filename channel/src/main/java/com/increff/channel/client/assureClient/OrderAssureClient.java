package com.increff.channel.client.assureClient;

import java.util.Arrays;
import java.util.List;
import com.increff.commons.model.OrderData;
import com.increff.commons.model.OrderDataChannel;
import com.increff.commons.model.OrderForm;
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

    public void addOrder(OrderForm orderForm) {
        restTemplate.postForObject("http://localhost:9000/assure/api/order", orderForm,
                ResponseEntity.class);
    }

    public OrderData getOrder(Long id) {
        ResponseEntity<OrderData> response = restTemplate
                .getForEntity("http://localhost:9000/assure/api/order/" + id, OrderData.class);
        return response.getBody();
    }

    public List<OrderDataChannel> getOrders() throws ApiException {
        ResponseEntity<OrderDataChannel[]> response = restTemplate.getForEntity(
                "http://localhost:9000/assure/api/order/channel", OrderDataChannel[].class);
        List<OrderDataChannel> orderDatas = Arrays.asList(response.getBody());
        System.out.print(orderDatas);
        return orderDatas;
    }

    // public void allocateOrder(Long id) {
    // restTemplate.put("http://localhost:9000/assure/api/order/allocate/" + id, null);
    // // restTemplate.getForEntity("http://localhost:9000/assure/api/order/allocate/" + id,
    // // ResponseEntity.class);
    // }

    // public void generateInvoiceOrder(Long id) {
    // restTemplate.put("http://localhost:9000/assure/api/order/generateinvoice/" + id, null);

    // // restTemplate.getForEntity("http://localhost:9000/assure/api/order/generateinvoice/" + id,
    // // ResponseEntity.class);
    // }

    // public byte[] downloadOrder(Long id) {
    // ResponseEntity<byte[]> response = restTemplate.getForEntity(
    // "http://localhost:9000/assure/api/order/download/" + id, byte[].class);
    // return response.getBody();
    // }


}
