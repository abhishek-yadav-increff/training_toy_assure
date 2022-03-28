package com.increff.assure.dto;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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

    @Test(expected = ApiException.class)
    public void testAddNullBinIndex() throws ApiException {

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(null,
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddInvalidBinIndex() throws ApiException {

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

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex() + Long.valueOf(
                1),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddNullClientSkuId() throws ApiException {

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
                null, productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddEmptyClientSkuId() throws ApiException {

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
                "", productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddInvalidClientSkuId() throws ApiException {

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
                productData.getClientSkuId() + "_invalid", productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddNullClientId() throws ApiException {

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
                productData.getClientSkuId(), null, Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddInvalidClientId() throws ApiException {

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
                productData.getClientSkuId(), productData.getClientId() + Long.valueOf(10), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddNullQuantity() throws ApiException {

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
                productData.getClientSkuId(), productData.getClientId(), null);
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddInvalidQuantity() throws ApiException {

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
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(-10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddDuplicate() throws ApiException {

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

        List<BinSkuForm> binSkuForms = new ArrayList<>();
        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuForms.add(binSkuForm);
        binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(12));
        binSkuForms.add(binSkuForm);

        binSkuDto.add(binSkuForms);
    }

    @Test
    public void testGetSuccess() throws ApiException {
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

        BinSkuData binSkuData = binSkuDatas.get(0);
        assertEquals(binSkuData.getBinId(), binIndexRange.getBgIndex());
        assertEquals(binSkuData.getGlobalSkuId(), productData.getGlobalSkuId());
        assertEquals(binSkuData.getQuantity(), Long.valueOf(10));
    }

    @Test
    public void testGetAllSuccess() throws ApiException {
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
        productForm = ProductUtils.getProductForm("clientSkuId2", clientData.getId(),
                "name2", "brandId2", 11.999, "description2");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);
        ProductData productData1 = productDatas.get(1);

        List<BinSkuForm> binSkuForms = new ArrayList<>();
        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuForms.add(binSkuForm);
        binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData1.getClientSkuId(), productData1.getClientId(), Long.valueOf(10));
        binSkuForms.add(binSkuForm);
        binSkuDto.add(binSkuForms);

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 2);
    }

    @Test
    public void testUpdateSuccess() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId",
                clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);

        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(20));
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
        BinSkuData binSkuData = binSkuDatas.get(0);
        assertEquals(binSkuData.getQuantity(), Long.valueOf(20));
    }

    @Test(expected = ApiException.class)
    public void testUpdateInvalidClientSkuId() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId",
                clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);

        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId() + "_e", productData.getClientId(), Long.valueOf(20));
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
        BinSkuData binSkuData = binSkuDatas.get(0);
        assertEquals(binSkuData.getQuantity(), Long.valueOf(20));
    }

    @Test(expected = ApiException.class)
    public void testUpdateInvalidClientId() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId",
                clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);

        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId() + Long.valueOf(1), Long.valueOf(20));
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
        BinSkuData binSkuData = binSkuDatas.get(0);
        assertEquals(binSkuData.getQuantity(), Long.valueOf(20));
    }

    @Test(expected = ApiException.class)
    public void testUpdateInvalidBinIndex() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId",
                clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);

        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        binSkuForm = BinSkuUtils.getBinSkuForm(null,
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(20));
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
        BinSkuData binSkuData = binSkuDatas.get(0);
        assertEquals(binSkuData.getQuantity(), Long.valueOf(20));
    }

    @Test(expected = ApiException.class)
    public void testUpdateInvalidQuantity() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId",
                clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);

        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(-20));
        binSkuDto.add(Arrays.asList(binSkuForm));

        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        assertEquals(binSkuDatas.size(), 1);
        BinSkuData binSkuData = binSkuDatas.get(0);
        assertEquals(binSkuData.getQuantity(), Long.valueOf(20));
    }

}
