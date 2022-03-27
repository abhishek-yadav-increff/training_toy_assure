package com.increff.assure.dto;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import com.increff.assure.model.BinForm;
import com.increff.assure.model.BinIndexRange;
import com.increff.assure.model.BinSkuData;
import com.increff.assure.model.BinSkuForm;
import com.increff.assure.model.ProductData;
import com.increff.assure.model.ProductForm;
import com.increff.assure.service.AbstractUnitTest;
import com.increff.assure.utils.BinSkuUtils;
import com.increff.assure.utils.BinUtils;
import com.increff.assure.utils.ClientUtil;
import com.increff.assure.utils.ProductUtils;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.ClientData;
import com.increff.commons.model.ClientForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * BinSkuDtoTest
 */
public class BinSkuDtoTest extends AbstractUnitTest {

    @Autowired
    private BinSkuDto binSkuDto;

    @Autowired
    private BinDto binDto;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private ClientDto clientDto;

    @Test
    public void testAddSuccess() throws ApiException {

        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
    }

}
