package com.increff.assure.dto;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import com.increff.assure.client.ChannelClient;
import com.increff.assure.dto.helper.CommonsHelper;
import com.increff.assure.dto.helper.OrderDtoHelper;
import com.increff.assure.dto.helper.OrderItemDtoHelper;
import com.increff.commons.enums.InvoiceEnum;
import com.increff.assure.pojo.ChannelListingPojo;
import com.increff.assure.pojo.ChannelPojo;
import com.increff.assure.pojo.OrderItemPojo;
import com.increff.assure.pojo.OrderPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.ChannelListingService;
import com.increff.assure.service.ChannelService;
import com.increff.assure.service.ClientService;
import com.increff.assure.service.OrderItemService;
import com.increff.assure.service.OrderService;
import com.increff.assure.service.ProductService;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.ErrorData;
import com.increff.commons.model.OrderData;
import com.increff.commons.model.OrderDataChannel;
import com.increff.commons.model.OrderForm;
import com.increff.commons.model.OrderItemForm;
import com.increff.commons.model.OrderItemXmlForm;
import com.increff.commons.model.OrderXmlForm;
import com.increff.commons.utils.PdfGenerationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderDto
 */
@Service
public class OrderDto {

    @Autowired
    private ChannelClient channelClient;

    @Autowired
    private OrderService orderService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ChannelListingService channelListingService;
    @Autowired
    private ProductService productService;

    private static final String pdfFolder =
            "/home/abhk943/Documents/increff/toy_assure/assure/xml_data/generated_pdf/";
    private static final String xlsModelPath =
            "/home/abhk943/Documents/increff/toy_assure/assure/src/main/resources/OrderPdfModel.xsl";

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderForm orderForm) throws ApiException {


        OrderPojo orderPojo = OrderDtoHelper.convert(orderForm);
        orderService.add(orderPojo);
        OrderPojo orderPojo2 = orderService.getByChannelOrderId(orderPojo.getChannelOrderId());

        validateOrderItems(orderForm.getOrderItemForms(), orderPojo2);
        for (OrderItemForm orderItemForm : orderForm.getOrderItemForms()) {
            ProductPojo productPojo =
                    productService.getClientIdClientSkuId(orderPojo2.getClientId(),
                            CommonsHelper.normalize(orderItemForm.getClientSkuId()));
            Long globalSkuId = productPojo.getGlobalSkuId();
            OrderItemPojo orderItemPojo =
                    OrderItemDtoHelper.convert(orderItemForm, globalSkuId, orderPojo2.getId());
            orderService.addOrderItem(orderItemPojo);
        }
    }

    private void validateOrderItems(List<OrderItemForm> orderItemForms, OrderPojo p)
            throws ApiException {
        List<ErrorData> errorDatas = new ArrayList<>();
        Long row = 1L;
        for (OrderItemForm oif : orderItemForms) {
            try {
                validateOrderItem(oif, p);
            } catch (Exception e) {
                errorDatas.add(new ErrorData("error", e.getMessage(), row));
            }
            row += 1;
        }
        if (!errorDatas.isEmpty())
            throw new ApiException("Failed validating Order Items!!", errorDatas);
    }

    private void validateOrderItem(OrderItemForm oif, OrderPojo p) throws ApiException {
        if (oif.getOrderedQuantity() == null)
            throw new ApiException("Ordered Quantity cant not be empty!!");
        if (oif.getOrderedQuantity() <= 0)
            throw new ApiException("Ordered Quantity must be postive!!");
        if (oif.getSellingPricePerUnit() == null)
            throw new ApiException("Selling Price Per Unit cant not be empty!!");
        if (oif.getSellingPricePerUnit() <= 0)
            throw new ApiException("Selling Price Per Unit must be postive!!");
        if (oif.getClientSkuId() == null)
            throw new ApiException("Client Sku ID Per Unit cant not be empty!!");
        ProductPojo productPojo = productService.getClientIdClientSkuId(p.getClientId(),
                CommonsHelper.normalize(oif.getClientSkuId()));
        if (productPojo == null)
            throw new ApiException(
                    "Product with Client ID, Client SKU ID combination doesn't exist!!");
    }


    public OrderData get(Long id) throws ApiException {
        OrderPojo orderPojo = orderService.get(id);
        return OrderDtoHelper.convert(orderPojo);
    }

    public List<OrderData> getAll() throws ApiException {
        List<OrderPojo> orderPojos = orderService.getAll();
        return OrderDtoHelper.convert(orderPojos);
    }

    public void allocate(Long id) throws ApiException {
        orderService.allocate(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void generateInvoice(Long id) throws ApiException {
        orderService.generateInvoice(id);

        OrderXmlForm orderXmlForm = getXmlForm(id);
        System.out.println(orderXmlForm);
        if (getInvoiceType(id).equals(InvoiceEnum.SELF)) {
            String fname = PdfGenerationHelper.generateXML(id, orderXmlForm);
            List<String> xsl_dir_pdf_paths = PdfGenerationHelper.generatePaths(fname, xlsModelPath);
            PdfGenerationHelper.generatePdf(fname, xsl_dir_pdf_paths);
        } else {
            // System.out.print("before calling client");
            channelClient.generateInvoiceOrder(orderXmlForm);
            // System.out.print("after calling client");

        }
    }

    private InvoiceEnum getInvoiceType(Long id) throws ApiException {
        OrderPojo orderPojo = orderService.get(id);
        ChannelPojo channelPojo = channelService.get(orderPojo.getChannelId());
        InvoiceEnum invoiceEnum = channelPojo.getInvoiceType();
        return invoiceEnum;
    }

    private OrderXmlForm getXmlForm(Long id) throws ApiException {
        OrderPojo p = orderService.get(id);
        List<OrderItemPojo> orderItemPojos = orderItemService.getByOrderId(id);
        List<OrderItemXmlForm> orderItemXmlForms =
                convertItems(orderItemPojos, p.getClientId(), p.getChannelId());
        String clientName = clientService.get(p.getClientId()).getName();
        String customerName = clientService.get(p.getCustomerId()).getName();
        String channelName = channelService.get(p.getChannelId()).getName();
        return OrderDtoHelper.convert(p, orderItemXmlForms, clientName, customerName, channelName);
    }

    private List<OrderItemXmlForm> convertItems(List<OrderItemPojo> orderItemPojos, Long clientId,
            Long channelId) throws ApiException {
        List<OrderItemXmlForm> orderItemXmlForms = new ArrayList<OrderItemXmlForm>();
        for (OrderItemPojo p : orderItemPojos) {
            System.out.println("oixp: " + orderItemXmlForms);
            ProductPojo productPojo = productService.get(p.getGlobalSkuId());
            System.out.println("PP: " + productPojo);
            ChannelListingPojo clp =
                    new ChannelListingPojo(clientId, channelId, p.getGlobalSkuId());
            System.out.println("CLP: " + clp);
            ChannelListingPojo channelListingPojo =
                    channelListingService.getByClientIdChanneIdGlobalSkuId(clp);
            System.out.println("CLP2: " + channelListingPojo);
            orderItemXmlForms.add(OrderItemDtoHelper.convertToForm(p, productPojo.getName(),
                    productPojo.getClientSkuId(), channelListingPojo.getChannelSkuId()));
        }
        return orderItemXmlForms;
    }

    public byte[] getPdf(Long id) throws ApiException {
        orderService.getPdf(id);
        OrderPojo orderPojo = orderService.get(id);
        if (getInvoiceType(id).equals(InvoiceEnum.SELF))
            return PdfGenerationHelper.getPdf(id, pdfFolder);
        else
            return channelClient.downloadOrder(orderPojo.getChannelOrderId());
    }

    public List<OrderDataChannel> getAllForChannel() throws ApiException {
        List<OrderPojo> orderPojos = orderService.getAllForChannel();
        List<OrderDataChannel> odc = new ArrayList<>();
        for (OrderPojo p : orderPojos) {
            String channelName = channelService.get(p.getChannelId()).getName();
            String clientName = clientService.get(p.getClientId()).getName();
            String customerName = clientService.get(p.getCustomerId()).getName();
            odc.add(OrderDtoHelper.convertForChannel(p, clientName, customerName, channelName));
        }
        return odc;
    }

    public void addForChannel(OrderForm orderForm) throws ApiException {
        OrderPojo orderPojo = OrderDtoHelper.convert(orderForm);
        orderService.add(orderPojo);

        OrderPojo orderPojo2 = orderService.getByChannelOrderId(orderPojo.getChannelOrderId());
        if (orderPojo2 == null)
            throw new ApiException("Error creating order!!");
        for (OrderItemForm orderItemForm : orderForm.getOrderItemForms()) {
            ChannelListingPojo chp = new ChannelListingPojo(orderPojo2.getClientId(),
                    orderPojo2.getChannelId(), orderItemForm.getChannelSkuId());
            ChannelListingPojo chp2 = channelListingService.getByClientIdChanneIdChannelSkuId(chp);
            OrderItemPojo orderItemPojo = OrderItemDtoHelper.convert(orderItemForm,
                    chp2.getGlobalSkuId(), orderPojo2.getId());
            orderService.addOrderItem(orderItemPojo);
        }
    }

    // public void update(Long id, OrderForm f) throws ApiException {
    // OrderPojo p = OrderDtoHelper.convert(f);
    // orderService.update(id, p);
    // }


}
