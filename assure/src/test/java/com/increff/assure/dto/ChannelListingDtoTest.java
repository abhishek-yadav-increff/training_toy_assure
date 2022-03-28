package com.increff.assure.dto;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import com.increff.assure.model.ChannelListingData;
import com.increff.assure.model.ChannelListingForm;
import com.increff.assure.model.ProductData;
import com.increff.assure.model.ProductForm;
import com.increff.assure.service.AbstractUnitTest;
import com.increff.assure.utils.ChannelListingUtils;
import com.increff.assure.utils.ChannelUtils;
import com.increff.assure.utils.ClientUtil;
import com.increff.assure.utils.ProductUtils;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.ChannelData;
import com.increff.commons.model.ChannelForm;
import com.increff.commons.model.ClientData;
import com.increff.commons.model.ClientForm;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ChannelListingDtoTest
 */
public class ChannelListingDtoTest extends AbstractUnitTest {
    @Autowired
    private ChannelListingDto channelListingDto;
    @Autowired
    private ClientDto clientDto;
    @Autowired
    private ChannelDto channelDto;
    @Autowired
    private ProductDto productDto;

    @Test
    public void testAddSuccess() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm(" channel   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ClientForm clientForm = ClientUtil.getClientForm(" naMe   ", " cliEnt ");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(), "channelSkuId",
                clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        List<ChannelListingData> channelListingDatas = channelListingDto.getAll();
        assertEquals(channelListingDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddNullChannelId() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm(" channel   ", " seLf ");
        channelDto.add(channelForm);

        ClientForm clientForm = ClientUtil.getClientForm(" naMe   ", " cliEnt ");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(null, "channelSkuId",
                clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        List<ChannelListingData> channelListingDatas = channelListingDto.getAll();
        assertEquals(channelListingDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddInvalidClientId() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm(" channel   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ClientForm clientForm = ClientUtil.getClientForm(" naMe   ", " cliEnt ");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(), "channelSkuId",
                null, productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        List<ChannelListingData> channelListingDatas = channelListingDto.getAll();
        assertEquals(channelListingDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddNullClientSkuId() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm(" channel   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ClientForm clientForm = ClientUtil.getClientForm(" naMe   ", " cliEnt ");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(), "channelSkuId",
                clientData.getId(), null);
        channelListingDto.add(Arrays.asList(clf));

        List<ChannelListingData> channelListingDatas = channelListingDto.getAll();
        assertEquals(channelListingDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddEmptyClientSkuId() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm(" channel   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ClientForm clientForm = ClientUtil.getClientForm(" naMe   ", " cliEnt ");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(), "channelSkuId",
                clientData.getId(), "");
        channelListingDto.add(Arrays.asList(clf));

        List<ChannelListingData> channelListingDatas = channelListingDto.getAll();
        assertEquals(channelListingDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddDuplicateChannelIdClientIdClientSkuId() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm(" channel   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ClientForm clientForm = ClientUtil.getClientForm(" naMe   ", " cliEnt ");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(), "channelSkuId",
                clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));
        clf = ChannelListingUtils.getChannelListingForm(channelData.getId(), "channelSkuId2",
                clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));
    }

    @Test(expected = ApiException.class)
    public void testAddDuplicateChannelIdClientIdChannelSkuId() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm(" channel   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ClientForm clientForm = ClientUtil.getClientForm(" naMe   ", " cliEnt ");
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

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(), "channelSkuId",
                clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));
        clf = ChannelListingUtils.getChannelListingForm(channelData.getId(), "channelSkuId",
                clientData.getId(), productData1.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

    }

    @Test
    public void testGetSuccess() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm(" channel   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ClientForm clientForm = ClientUtil.getClientForm(" naMe   ", " cliEnt ");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(), "channelSkuId",
                clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        List<ChannelListingData> channelListingDatas = channelListingDto.getAll();
        assertEquals(channelListingDatas.size(), 1);
        ChannelListingData cld = channelListingDatas.get(0);
        assertEquals(cld.getChannelId(), channelData.getId());
        assertEquals(cld.getChannelSkuId(), "channelSkuId");
        assertEquals(cld.getClientId(), clientData.getId());
        assertEquals(cld.getGlobalSkuId(), productData.getGlobalSkuId());
    }

    @Test
    public void testGetAllSuccess() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm(" channel   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ClientForm clientForm = ClientUtil.getClientForm(" naMe   ", " cliEnt ");
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

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(), "channelSkuId",
                clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));
        clf = ChannelListingUtils.getChannelListingForm(channelData.getId(), "channelSkuId2",
                clientData.getId(), productData1.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));
        List<ChannelListingData> channelListingDatas = channelListingDto.getAll();
        assertEquals(channelListingDatas.size(), 2);
    }

}