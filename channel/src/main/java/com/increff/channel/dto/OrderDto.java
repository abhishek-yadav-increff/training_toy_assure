package com.increff.channel.dto;

import java.util.List;
import com.increff.channel.client.assureClient.OrderAssureClient;
import com.increff.commons.model.OrderData;
import com.increff.commons.model.OrderForm;
import com.increff.commons.model.OrderXmlForm;
import com.increff.commons.utils.PdfGenerationHelper;
import com.increff.commons.model.ApiException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderDto
 */
@Service
public class OrderDto {

    @Autowired
    private OrderAssureClient orderAssureClient;

    private static final Logger LOGGER = Logger.getLogger(OrderDto.class);

    private static final String pdfFolder =
            "/home/abhk943/Documents/increff/toy_assure/channel/xml_data/generated_pdf/";
    private static final String xlsModelPath =
            "/home/abhk943/Documents/increff/toy_assure/channel/src/main/resources/OrderPdfModel.xsl";

    public void add(OrderForm orderForm) throws ApiException {
        LOGGER.info("In OrderService:add()");
        LOGGER.info("Form data received: " + orderForm.toString());
        orderAssureClient.addOrder(orderForm);

    }

    public OrderData get(Long id) throws ApiException {
        return orderAssureClient.getOrder(id);
    }

    public List<OrderData> getAll() throws ApiException {
        return orderAssureClient.getOrders();
    }

    public void generateInvoice(OrderXmlForm orderXmlForm) throws ApiException {
        String fname =
                PdfGenerationHelper.generateXML(orderXmlForm.getChannelOrderId(), orderXmlForm);
        System.out.println("xml geberated " + fname);
        List<String> xsl_dir_pdf_paths = PdfGenerationHelper.generatePaths(fname, xlsModelPath);
        PdfGenerationHelper.generatePdf(fname, xsl_dir_pdf_paths);
    }

    public byte[] getPdf(String id) throws ApiException {
        return PdfGenerationHelper.getPdf(id, pdfFolder);
    }
}
