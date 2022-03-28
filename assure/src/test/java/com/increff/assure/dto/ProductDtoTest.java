package com.increff.assure.dto;

import static org.junit.Assert.assertEquals;
import java.util.List;
import com.increff.assure.model.ProductData;
import com.increff.assure.model.ProductForm;
import com.increff.assure.service.AbstractUnitTest;
import com.increff.assure.utils.ClientUtil;
import com.increff.assure.utils.ProductUtils;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.ClientData;
import com.increff.commons.model.ClientForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ProductDtoTest
 */
public class ProductDtoTest extends AbstractUnitTest {

    @Autowired
    private ProductDto productDto;

    @Autowired
    private ClientDto clientDto;

    @Test
    public void addSuccess() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        assertEquals(productDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void addNullName() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                null, "brandId", 10.999, "description");
        productDto.add(productForm);
    }

    @Test(expected = ApiException.class)
    public void addEmptyName() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(), "",
                "brandId", 10.999, "description");
        productDto.add(productForm);
    }

    @Test(expected = ApiException.class)
    public void addNullClientSkuId() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", null);
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
    }

    @Test(expected = ApiException.class)
    public void addEmptyClientSkuId() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
    }

    @Test(expected = ApiException.class)
    public void addNullClientId() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", null, "name",
                "brandId", 10.999, "description");
        productDto.add(productForm);
    }

    @Test(expected = ApiException.class)
    public void addNullBrandId() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", null, 10.999, "description");
        productDto.add(productForm);
    }

    @Test(expected = ApiException.class)
    public void addEmptyBrandId() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "", 10.999, "description");
        productDto.add(productForm);
    }

    @Test(expected = ApiException.class)
    public void addNullDescription() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, null);
        productDto.add(productForm);
    }

    @Test(expected = ApiException.class)
    public void addEmptyDescription() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "");
        productDto.add(productForm);
    }

    @Test(expected = ApiException.class)
    public void addInvalidClientId() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId",
                clientData.getId() + Long.valueOf(1), "name", "brandId", 10.999, "description");
        productDto.add(productForm);
    }

    @Test(expected = ApiException.class)
    public void addDuplicateClientIdClientSkuId() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(), "name2",
                "brandId2", 11.999, "description2");
        productDto.add(productForm);
    }

    @Test()
    public void getSuccess() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "naMe   ", "brandId", 10.999, "   Description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        assertEquals(productDatas.size(), 1);
        ProductData productData = productDatas.get(0);

        assertEquals(productData.getClientSkuId(), "clientSkuId");
        assertEquals(productData.getClientId(), clientData.getId());
        assertEquals(productData.getName(), "name");
        assertEquals(productData.getBrandId(), "brandId");
        double precision = 0.001d;
        assertEquals(productData.getMrp(), 11.00, precision);
        assertEquals(productData.getDescription(), "description");
    }

    @Test()
    public void getAllSuccess() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "naMe   ", "brandId", 10.999, "   Description");
        productDto.add(productForm);
        productForm = ProductUtils.getProductForm("clientSkuId2", clientData.getId(),
                "name2", "brandId2", 11.999, "description2");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        assertEquals(productDatas.size(), 2);
    }

    @Test()
    public void updateSuccess() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "naMe   ", "brandId", 10.999, "   Description");
        productDto.add(productForm);
        productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name2", "brandId2", 11.999, "description2");
        List<ProductData> productDatas = productDto.getAll();
        assertEquals(productDatas.size(), 1);

        ProductData productData = productDatas.get(0);
        productDto.update(productData.getGlobalSkuId(), productForm);
        productData = productDto.get(productData.getGlobalSkuId());
        assertEquals(productData.getName(), "name2");
        assertEquals(productData.getBrandId(), "brandId2");
        double precision = 0.001d;
        assertEquals(productData.getMrp(), 12.00, precision);
        assertEquals(productData.getDescription(), "description2");
    }

}
