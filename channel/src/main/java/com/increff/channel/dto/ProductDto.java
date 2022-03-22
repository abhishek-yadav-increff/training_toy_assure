package com.increff.channel.dto;

import com.increff.channel.client.assureClient.ProductAssureClient;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.ProductData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ProductDto
 */
@Service
public class ProductDto {

    @Autowired
    private ProductAssureClient productAssureClient;

    public ProductData getClientIdClientSkuId(Long clientId, String clientSkuId)
            throws ApiException {
        return productAssureClient.getClientIdClientSkuId(clientId, clientSkuId);
    }

}
