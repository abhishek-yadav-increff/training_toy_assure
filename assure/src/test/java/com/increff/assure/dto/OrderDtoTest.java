package com.increff.assure.dto;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import com.increff.assure.model.BinForm;
import com.increff.assure.model.BinIndexRange;
import com.increff.assure.model.BinSkuData;
import com.increff.assure.model.BinSkuForm;
import com.increff.assure.model.ChannelListingData;
import com.increff.assure.model.ChannelListingForm;
import com.increff.assure.model.ProductData;
import com.increff.assure.model.ProductForm;
import com.increff.assure.service.AbstractUnitTest;
import com.increff.assure.utils.BinSkuUtils;
import com.increff.assure.utils.BinUtils;
import com.increff.assure.utils.ChannelListingUtils;
import com.increff.assure.utils.ChannelUtils;
import com.increff.assure.utils.ClientUtil;
import com.increff.assure.utils.OrderItemUtils;
import com.increff.assure.utils.OrderUtils;
import com.increff.assure.utils.ProductUtils;
import com.increff.commons.enums.OrderEnum;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.ChannelData;
import com.increff.commons.model.ChannelForm;
import com.increff.commons.model.ClientData;
import com.increff.commons.model.ClientForm;
import com.increff.commons.model.OrderData;
import com.increff.commons.model.OrderForm;
import com.increff.commons.model.OrderItemData;
import com.increff.commons.model.OrderItemForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * OrderDtoTest
 */
public class OrderDtoTest extends AbstractUnitTest {
    @Autowired
    private ChannelListingDto channelListingDto;
    @Autowired
    private ClientDto clientDto;
    @Autowired
    private ChannelDto channelDto;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private BinSkuDto binSkuDto;
    @Autowired
    private BinDto binDto;
    @Autowired
    private OrderItemDto orderItemDto;
    @Autowired
    private OrderDto orderDto;

    @Test
    public void testAddSelfSucess() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm("name", "customer");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);
        ClientData customerData = clientDatas.get(1);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));
        List<BinSkuData> binSkuDatas = binSkuDto.getAll();
        BinSkuData binSkuData = binSkuDatas.get(0);

        ChannelForm channelForm = ChannelUtils.getChannelForm(" internal   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
                "channelSkuId", clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));
        List<ChannelListingData> channelListingDatas = channelListingDto.getAll();
        ChannelListingData channelListingData = channelListingDatas.get(0);

        OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
                Long.valueOf(1), 0.999);

        OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
                channelData.getId(), "channelOrderId", Arrays.asList(oif));

        System.out.println(binIndexRange);
        System.out.println(clientData);
        System.out.println(customerData);
        System.out.println(productData);
        System.out.println(binSkuData);
        System.out.println(channelData);
        System.out.println(channelListingData);
        System.out.println(orderForm);

        orderDto.add(orderForm);
        List<OrderData> orderDatas = orderDto.getAll();
        assertEquals(orderDatas.size(), 1);
    }

    @Test(expected = ApiException.class)
    public void testAddSelfInvalidClientId() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm("name", "customer");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);
        ClientData customerData = clientDatas.get(1);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        ChannelForm channelForm = ChannelUtils.getChannelForm(" internal   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
                "channelSkuId", clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
                Long.valueOf(1), 0.999);

        OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId() + Long.valueOf(1),
                customerData.getId(), channelData.getId(), "channelOrderId", Arrays.asList(oif));

        orderDto.add(orderForm);
    }

    @Test(expected = ApiException.class)
    public void testAddSelfInvalidCustomerId() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm("name", "customer");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);
        ClientData customerData = clientDatas.get(1);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        ChannelForm channelForm = ChannelUtils.getChannelForm(" internal   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
                "channelSkuId", clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
                Long.valueOf(1), 0.999);

        OrderForm orderForm =
                OrderUtils.getOrderForm(clientData.getId(), customerData.getId() + Long.valueOf(1),
                        channelData.getId(), "channelOrderId", Arrays.asList(oif));

        orderDto.add(orderForm);

    }

    @Test(expected = ApiException.class)
    public void testAddSelfInvalidChannelId() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm("name", "customer");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);
        ClientData customerData = clientDatas.get(1);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        ChannelForm channelForm = ChannelUtils.getChannelForm(" internal   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
                "channelSkuId", clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
                Long.valueOf(1), 0.999);

        OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
                channelData.getId() + Long.valueOf(1), "channelOrderId", Arrays.asList(oif));

        orderDto.add(orderForm);
    }

    @Test(expected = ApiException.class)
    public void testAddSelfInvalidClientSkuId() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm("name", "customer");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);
        ClientData customerData = clientDatas.get(1);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        ChannelForm channelForm = ChannelUtils.getChannelForm(" internal   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
                "channelSkuId", clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        OrderItemForm oif = OrderItemUtils.getOrderItemForm(null, null, Long.valueOf(1), 0.999);

        OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
                channelData.getId(), "channelOrderId", Arrays.asList(oif));

        orderDto.add(orderForm);

    }

    @Test(expected = ApiException.class)
    public void testAddSelfInvalidOrderedQuantity() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm("name", "customer");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);
        ClientData customerData = clientDatas.get(1);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        ChannelForm channelForm = ChannelUtils.getChannelForm(" internal   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
                "channelSkuId", clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
                Long.valueOf(-1), 0.999);

        OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
                channelData.getId(), "channelOrderId", Arrays.asList(oif));

        orderDto.add(orderForm);

    }

    @Test(expected = ApiException.class)
    public void testAddSelfInvalidSellingPricePerUnit() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm("name", "customer");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);
        ClientData customerData = clientDatas.get(1);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        ChannelForm channelForm = ChannelUtils.getChannelForm(" internal   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
                "channelSkuId", clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
                Long.valueOf(1), -0.999);

        OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
                channelData.getId(), "channelOrderId", Arrays.asList(oif));

        orderDto.add(orderForm);

    }

    @Test(expected = ApiException.class)
    public void testAddSelfInvalidOrderItemForm() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm("name", "customer");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);
        ClientData customerData = clientDatas.get(1);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        ChannelForm channelForm = ChannelUtils.getChannelForm(" internal   ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
                "channelSkuId", clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
                channelData.getId(), "channelOrderId", null);

        orderDto.add(orderForm);

    }

    @Test
    public void testGetSucess() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm("name", "customer");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);
        ClientData customerData = clientDatas.get(1);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        ChannelForm channelForm = ChannelUtils.getChannelForm(" internal ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
                "channelSkuId", clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
                Long.valueOf(1), 0.999);

        OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
                channelData.getId(), "channelOrderId", Arrays.asList(oif));

        orderDto.add(orderForm);
        List<OrderData> orderDatas = orderDto.getAll();
        assertEquals(orderDatas.size(), 1);
        OrderData orderData = orderDatas.get(0);
        assertEquals(orderData.getChannelId(), channelData.getId());
        assertEquals(orderData.getClientId(), clientData.getId());
        assertEquals(orderData.getCustomerId(), customerData.getId());
        assertEquals(orderData.getChannelOrderId(), "channelOrderId");
        assertEquals(orderData.getStatus(), OrderEnum.CREATED.toString());

        List<OrderItemData> orderItemDatas = orderItemDto.getByOrderId(orderData.getId());
        assertEquals(orderItemDatas.size(), 1);
        OrderItemData oid = orderItemDatas.get(0);
        assertEquals(oid.getAllocatedQuantity(), Long.valueOf(0));
        assertEquals(oid.getFulfilledQuantity(), Long.valueOf(0));
        assertEquals(oid.getOrderedQuantity(), Long.valueOf(1));
        assertEquals(oid.getGlobalSkuId(), productData.getGlobalSkuId());
        assertEquals(oid.getSellingPricePerUnit(), Double.valueOf(1.00));
    }

    @Test
    public void testGetAllSuccess() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm("name", "customer");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);
        ClientData customerData = clientDatas.get(1);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        ChannelForm channelForm = ChannelUtils.getChannelForm(" internal ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
                "channelSkuId", clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
                Long.valueOf(1), 0.999);

        OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
                channelData.getId(), "channelOrderId", Arrays.asList(oif));

        orderDto.add(orderForm);
        orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
                channelData.getId(), "channelOrderId2", Arrays.asList(oif));
        orderDto.add(orderForm);
        List<OrderData> orderDatas = orderDto.getAll();
        assertEquals(orderDatas.size(), 2);
    }

    @Test(expected = ApiException.class)
    public void testAddDuplicateChannelOrderId() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm("name", "customer");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);
        ClientData customerData = clientDatas.get(1);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        ChannelForm channelForm = ChannelUtils.getChannelForm(" internal ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
                "channelSkuId", clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
                Long.valueOf(1), 0.999);

        OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
                channelData.getId(), "channelOrderId", Arrays.asList(oif));

        orderDto.add(orderForm);
        orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
                channelData.getId(), "channelOrderId", Arrays.asList(oif));
        orderDto.add(orderForm);

    }

    @Test
    public void testAllocateSuccess() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm("name", "customer");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);
        ClientData customerData = clientDatas.get(1);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        ChannelForm channelForm = ChannelUtils.getChannelForm(" internal ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
                "channelSkuId", clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
                Long.valueOf(1), 0.999);

        OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
                channelData.getId(), "channelOrderId", Arrays.asList(oif));

        orderDto.add(orderForm);
        OrderData orderData = orderDto.getAll().get(0);

        List<OrderItemData> orderItemDatas = orderItemDto.getByOrderId(orderData.getId());
        OrderItemData oid = orderItemDatas.get(0);
        assertEquals(oid.getAllocatedQuantity(), Long.valueOf(0));
        assertEquals(oid.getFulfilledQuantity(), Long.valueOf(0));
        assertEquals(oid.getOrderedQuantity(), Long.valueOf(1));

        orderDto.allocate(orderData.getId());
        OrderData orderData2 = orderDto.getAll().get(0);
        OrderItemData oid2 = orderItemDto.getByOrderId(orderData.getId()).get(0);
        assertEquals(oid2.getAllocatedQuantity(), Long.valueOf(1));
        assertEquals(oid2.getFulfilledQuantity(), Long.valueOf(0));
        assertEquals(oid2.getOrderedQuantity(), Long.valueOf(0));
        assertEquals(orderData2.getStatus(), OrderEnum.ALLOCATED.toString());
    }

    @Test
    public void testAllocateHalf() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm("name", "customer");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);
        ClientData customerData = clientDatas.get(1);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(5));
        binSkuDto.add(Arrays.asList(binSkuForm));

        ChannelForm channelForm = ChannelUtils.getChannelForm(" internal ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
                "channelSkuId", clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
                Long.valueOf(10), 0.999);

        OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
                channelData.getId(), "channelOrderId", Arrays.asList(oif));

        orderDto.add(orderForm);
        OrderData orderData = orderDto.getAll().get(0);

        List<OrderItemData> orderItemDatas = orderItemDto.getByOrderId(orderData.getId());
        OrderItemData oid = orderItemDatas.get(0);
        assertEquals(oid.getAllocatedQuantity(), Long.valueOf(0));
        assertEquals(oid.getFulfilledQuantity(), Long.valueOf(0));
        assertEquals(oid.getOrderedQuantity(), Long.valueOf(10));

        orderDto.allocate(orderData.getId());
        OrderData orderData2 = orderDto.getAll().get(0);
        OrderItemData oid2 = orderItemDto.getByOrderId(orderData.getId()).get(0);
        assertEquals(oid2.getAllocatedQuantity(), Long.valueOf(5));
        assertEquals(oid2.getFulfilledQuantity(), Long.valueOf(0));
        assertEquals(orderData2.getStatus(), OrderEnum.CREATED.toString());
        assertEquals(oid2.getOrderedQuantity(), Long.valueOf(5));
    }

    @Test
    public void testFulfillSucess() throws ApiException {
        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);
        BinIndexRange binIndexRange = binDto.getRange();

        ClientForm clientForm = ClientUtil.getClientForm("name", "client");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm("name", "customer");
        clientDto.add(clientForm);
        List<ClientData> clientDatas = clientDto.getAll();
        ClientData clientData = clientDatas.get(0);
        ClientData customerData = clientDatas.get(1);

        ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
                "name", "brandId", 10.999, "description");
        productDto.add(productForm);
        List<ProductData> productDatas = productDto.getAll();
        ProductData productData = productDatas.get(0);

        BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
                productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
        binSkuDto.add(Arrays.asList(binSkuForm));

        ChannelForm channelForm = ChannelUtils.getChannelForm(" internal ", " seLf ");
        channelDto.add(channelForm);
        List<ChannelData> channelDatas = channelDto.getAll();
        ChannelData channelData = channelDatas.get(0);

        ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
                "channelSkuId", clientData.getId(), productData.getClientSkuId());
        channelListingDto.add(Arrays.asList(clf));

        OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
                Long.valueOf(1), 0.999);

        OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
                channelData.getId(), "channelOrderId", Arrays.asList(oif));

        orderDto.add(orderForm);
        OrderData orderData = orderDto.getAll().get(0);

        List<OrderItemData> orderItemDatas = orderItemDto.getByOrderId(orderData.getId());
        OrderItemData oid = orderItemDatas.get(0);
        assertEquals(oid.getAllocatedQuantity(), Long.valueOf(0));
        assertEquals(oid.getFulfilledQuantity(), Long.valueOf(0));
        assertEquals(oid.getOrderedQuantity(), Long.valueOf(1));

        orderDto.allocate(orderData.getId());
        orderDto.generateInvoice(orderData.getId());
        OrderData orderData2 = orderDto.getAll().get(0);
        OrderItemData oid2 = orderItemDto.getByOrderId(orderData.getId()).get(0);
        assertEquals(oid2.getAllocatedQuantity(), Long.valueOf(0));
        assertEquals(oid2.getFulfilledQuantity(), Long.valueOf(1));
        assertEquals(oid2.getOrderedQuantity(), Long.valueOf(0));
        assertEquals(orderData2.getStatus(), OrderEnum.FULFILLED.toString());
    }

    // @Test
    // public void testAddSelfInvalidClientId() throws ApiException {
    // BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
    // binDto.add(binForm);
    // BinIndexRange binIndexRange = binDto.getRange();

    // ClientForm clientForm = ClientUtil.getClientForm("name", "client");
    // clientDto.add(clientForm);
    // clientForm = ClientUtil.getClientForm("name", "customer");
    // clientDto.add(clientForm);
    // List<ClientData> clientDatas = clientDto.getAll();
    // ClientData clientData = clientDatas.get(0);
    // ClientData customerData = clientDatas.get(1);

    // ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
    // "name", "brandId", 10.999, "description");
    // productDto.add(productForm);
    // List<ProductData> productDatas = productDto.getAll();
    // ProductData productData = productDatas.get(0);

    // BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
    // productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
    // binSkuDto.add(Arrays.asList(binSkuForm));
    // List<BinSkuData> binSkuDatas = binSkuDto.getAll();
    // BinSkuData binSkuData = binSkuDatas.get(0);

    // ChannelForm channelForm = ChannelUtils.getChannelForm(" internal ", " seLf ");
    // channelDto.add(channelForm);
    // List<ChannelData> channelDatas = channelDto.getAll();
    // ChannelData channelData = channelDatas.get(0);

    // ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
    // "channelSkuId", clientData.getId(), productData.getClientSkuId());
    // channelListingDto.add(Arrays.asList(clf));
    // List<ChannelListingData> channelListingDatas = channelListingDto.getAll();
    // ChannelListingData channelListingData = channelListingDatas.get(0);

    // OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
    // Long.valueOf(1), 0.999);

    // OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
    // channelData.getId(), "channelOrderId", Arrays.asList(oif));

    // orderDto.add(orderForm);
    // List<OrderData> orderDatas = orderDto.getAll();
    // assertEquals(orderDatas.size(), 1);
    // }

    // @Test
    // public void testAddSelfInvalidClientId() throws ApiException {
    // BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
    // binDto.add(binForm);
    // BinIndexRange binIndexRange = binDto.getRange();

    // ClientForm clientForm = ClientUtil.getClientForm("name", "client");
    // clientDto.add(clientForm);
    // clientForm = ClientUtil.getClientForm("name", "customer");
    // clientDto.add(clientForm);
    // List<ClientData> clientDatas = clientDto.getAll();
    // ClientData clientData = clientDatas.get(0);
    // ClientData customerData = clientDatas.get(1);

    // ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
    // "name", "brandId", 10.999, "description");
    // productDto.add(productForm);
    // List<ProductData> productDatas = productDto.getAll();
    // ProductData productData = productDatas.get(0);

    // BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
    // productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
    // binSkuDto.add(Arrays.asList(binSkuForm));
    // List<BinSkuData> binSkuDatas = binSkuDto.getAll();
    // BinSkuData binSkuData = binSkuDatas.get(0);

    // ChannelForm channelForm = ChannelUtils.getChannelForm(" internal ", " seLf ");
    // channelDto.add(channelForm);
    // List<ChannelData> channelDatas = channelDto.getAll();
    // ChannelData channelData = channelDatas.get(0);

    // ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
    // "channelSkuId", clientData.getId(), productData.getClientSkuId());
    // channelListingDto.add(Arrays.asList(clf));
    // List<ChannelListingData> channelListingDatas = channelListingDto.getAll();
    // ChannelListingData channelListingData = channelListingDatas.get(0);

    // OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
    // Long.valueOf(1), 0.999);

    // OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
    // channelData.getId(), "channelOrderId", Arrays.asList(oif));

    // orderDto.add(orderForm);
    // List<OrderData> orderDatas = orderDto.getAll();
    // assertEquals(orderDatas.size(), 1);
    // }

    // @Test
    // public void testAddSelfInvalidClientId() throws ApiException {
    // BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
    // binDto.add(binForm);
    // BinIndexRange binIndexRange = binDto.getRange();

    // ClientForm clientForm = ClientUtil.getClientForm("name", "client");
    // clientDto.add(clientForm);
    // clientForm = ClientUtil.getClientForm("name", "customer");
    // clientDto.add(clientForm);
    // List<ClientData> clientDatas = clientDto.getAll();
    // ClientData clientData = clientDatas.get(0);
    // ClientData customerData = clientDatas.get(1);

    // ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
    // "name", "brandId", 10.999, "description");
    // productDto.add(productForm);
    // List<ProductData> productDatas = productDto.getAll();
    // ProductData productData = productDatas.get(0);

    // BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
    // productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
    // binSkuDto.add(Arrays.asList(binSkuForm));
    // List<BinSkuData> binSkuDatas = binSkuDto.getAll();
    // BinSkuData binSkuData = binSkuDatas.get(0);

    // ChannelForm channelForm = ChannelUtils.getChannelForm(" internal ", " seLf ");
    // channelDto.add(channelForm);
    // List<ChannelData> channelDatas = channelDto.getAll();
    // ChannelData channelData = channelDatas.get(0);

    // ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
    // "channelSkuId", clientData.getId(), productData.getClientSkuId());
    // channelListingDto.add(Arrays.asList(clf));
    // List<ChannelListingData> channelListingDatas = channelListingDto.getAll();
    // ChannelListingData channelListingData = channelListingDatas.get(0);

    // OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
    // Long.valueOf(1), 0.999);

    // OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
    // channelData.getId(), "channelOrderId", Arrays.asList(oif));

    // orderDto.add(orderForm);
    // List<OrderData> orderDatas = orderDto.getAll();
    // assertEquals(orderDatas.size(), 1);
    // }

    // @Test
    // public void testAddSelfInvalidClientId() throws ApiException {
    // BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
    // binDto.add(binForm);
    // BinIndexRange binIndexRange = binDto.getRange();

    // ClientForm clientForm = ClientUtil.getClientForm("name", "client");
    // clientDto.add(clientForm);
    // clientForm = ClientUtil.getClientForm("name", "customer");
    // clientDto.add(clientForm);
    // List<ClientData> clientDatas = clientDto.getAll();
    // ClientData clientData = clientDatas.get(0);
    // ClientData customerData = clientDatas.get(1);

    // ProductForm productForm = ProductUtils.getProductForm("clientSkuId", clientData.getId(),
    // "name", "brandId", 10.999, "description");
    // productDto.add(productForm);
    // List<ProductData> productDatas = productDto.getAll();
    // ProductData productData = productDatas.get(0);

    // BinSkuForm binSkuForm = BinSkuUtils.getBinSkuForm(binIndexRange.getBgIndex(),
    // productData.getClientSkuId(), productData.getClientId(), Long.valueOf(10));
    // binSkuDto.add(Arrays.asList(binSkuForm));
    // List<BinSkuData> binSkuDatas = binSkuDto.getAll();
    // BinSkuData binSkuData = binSkuDatas.get(0);

    // ChannelForm channelForm = ChannelUtils.getChannelForm(" internal ", " seLf ");
    // channelDto.add(channelForm);
    // List<ChannelData> channelDatas = channelDto.getAll();
    // ChannelData channelData = channelDatas.get(0);

    // ChannelListingForm clf = ChannelListingUtils.getChannelListingForm(channelData.getId(),
    // "channelSkuId", clientData.getId(), productData.getClientSkuId());
    // channelListingDto.add(Arrays.asList(clf));
    // List<ChannelListingData> channelListingDatas = channelListingDto.getAll();
    // ChannelListingData channelListingData = channelListingDatas.get(0);

    // OrderItemForm oif = OrderItemUtils.getOrderItemForm(productData.getClientSkuId(), null,
    // Long.valueOf(1), 0.999);

    // OrderForm orderForm = OrderUtils.getOrderForm(clientData.getId(), customerData.getId(),
    // channelData.getId(), "channelOrderId", Arrays.asList(oif));

    // orderDto.add(orderForm);
    // List<OrderData> orderDatas = orderDto.getAll();
    // assertEquals(orderDatas.size(), 1);
    // }

}
