package com.increff.channel.client.assureClient;

import com.increff.commons.model.ProductData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * AssureProduct
 */
@Service
public class ProductAssureClient {

    @Autowired
    private RestTemplate restTemplate;

    public ProductData getClientIdClientSkuId(Long clientId, String clientSkuId) {
        ResponseEntity<ProductData> response = restTemplate.getForEntity(
                "http://localhost:9000/assure/api/channel/" + clientId + "/" + clientSkuId,
                ProductData.class);
        return response.getBody();
    }

}
