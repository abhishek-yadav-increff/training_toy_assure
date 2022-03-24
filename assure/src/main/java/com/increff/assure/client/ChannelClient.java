package com.increff.assure.client;

import com.increff.commons.model.ApiException;
import com.increff.commons.model.OrderXmlForm;
import com.increff.commons.utils.ApiExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * AssureClient
 */
@Service
public class ChannelClient {

    @Autowired
    private RestTemplate restTemplate;

    public void generateInvoiceOrder(OrderXmlForm orderXmlForm) throws ApiException {
        try {
            HttpEntity<OrderXmlForm> requestUpdate = new HttpEntity<>(orderXmlForm, null);
            restTemplate.exchange("http://localhost:9001/channel/api/order/generateinvoice",
                    HttpMethod.PUT, requestUpdate, Void.class);
        } catch (HttpStatusCodeException ex) {
            throw ApiExceptionHelper.handleHttpException(ex);
        }
    }

    public byte[] downloadOrder(Long id) throws ApiException {
        try {
            ResponseEntity<byte[]> response = restTemplate.getForEntity(
                    "http://localhost:9001/channel/api/order/download/" + id, byte[].class);
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            throw ApiExceptionHelper.handleHttpException(ex);
        }
    }


}
