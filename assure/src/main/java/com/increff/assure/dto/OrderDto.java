package com.increff.assure.dto;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import com.increff.assure.dto.helper.CommonsHelper;
import com.increff.assure.dto.helper.OrderDtoHelper;
import com.increff.assure.dto.helper.OrderItemDtoHelper;
import com.increff.assure.model.OrderData;
import com.increff.assure.model.OrderForm;
import com.increff.assure.model.OrderItemForm;
import com.increff.assure.model.OrderItemXmlForm;
import com.increff.assure.model.OrderXmlForm;
import com.increff.assure.pojo.OrderItemPojo;
import com.increff.assure.pojo.OrderPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.OrderItemService;
import com.increff.assure.service.OrderService;
import com.increff.assure.service.ProductService;
import com.increff.commons.model.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderDto
 */
@Service
public class OrderDto {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderForm orderForm) throws ApiException {


        OrderPojo orderPojo = OrderDtoHelper.convert(orderForm);
        orderService.add(orderPojo);

        OrderPojo orderPojo2 = orderService.getByChannelOrderId(orderPojo.getChannelOrderId());
        if (orderPojo2 == null)
            throw new ApiException("Error creating order!!");
        for (OrderItemForm orderItemForm : orderForm.getOrderItemForms()) {
            ProductPojo productPojo =
                    productService.getClientIdClientSkuId(orderPojo2.getClientId(),
                            CommonsHelper.normalize(orderItemForm.getClientSkuId()));
            Long globalSkuId = null;
            if (productPojo != null)
                globalSkuId = productPojo.getGlobalSkuId();
            OrderItemPojo orderItemPojo =
                    OrderItemDtoHelper.convert(orderItemForm, globalSkuId, orderPojo2.getId());
            orderService.addOrderItem(orderItemPojo);
        }
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
        String fname = OrderDtoHelper.generateXML(id, orderXmlForm);
        List<String> xsl_dir_pdf_paths = OrderDtoHelper.generatePaths(fname);
        OrderDtoHelper.generatePdf(fname, xsl_dir_pdf_paths);
    }

    public OrderXmlForm getXmlForm(Long id) throws ApiException {
        OrderPojo p = orderService.get(id);
        List<OrderItemPojo> orderItemPojos = orderItemService.getByOrderId(id);
        List<OrderItemXmlForm> orderItemXmlForms = convertItems(orderItemPojos);
        return OrderDtoHelper.convert(p, orderItemXmlForms);
    }

    private List<OrderItemXmlForm> convertItems(List<OrderItemPojo> orderItemPojos)
            throws ApiException {
        List<OrderItemXmlForm> orderItemXmlForms = new ArrayList<OrderItemXmlForm>();
        for (OrderItemPojo p : orderItemPojos) {
            orderItemXmlForms.add(OrderItemDtoHelper.convertToForm(p));
        }
        return orderItemXmlForms;
    }

    public byte[] getPdf(Long id) throws ApiException {
        return OrderDtoHelper.getPdf(id);
    }

    // public void update(Long id, OrderForm f) throws ApiException {
    // OrderPojo p = OrderDtoHelper.convert(f);
    // orderService.update(id, p);
    // }


}
