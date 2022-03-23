package com.increff.assure.dto.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import com.increff.assure.model.OrderData;
import com.increff.assure.model.OrderForm;
import com.increff.assure.model.OrderItemXmlForm;
import com.increff.assure.model.OrderXmlForm;
import com.increff.assure.pojo.OrderPojo;
import com.increff.commons.model.ApiException;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderDtoHelper
 */
public class OrderDtoHelper {

    public static OrderData convert(OrderPojo orderPojo) {
        OrderData orderData = new OrderData();
        orderData.setId(orderPojo.getId());
        orderData.setChannelId(orderPojo.getChannelId());
        orderData.setChannelOrderId(orderPojo.getChannelOrderId());
        orderData.setClientId(orderPojo.getClientId());
        orderData.setCustomerId(orderPojo.getCustomerId());
        orderData.setStatus(orderPojo.getStatus().toString());
        return orderData;
    }

    public static List<OrderData> convert(List<OrderPojo> orderPojos) {
        List<OrderData> orderDatas = new ArrayList<OrderData>();
        for (OrderPojo orderPojo : orderPojos) {
            orderDatas.add(convert(orderPojo));
        }
        return orderDatas;
    }

    public static OrderPojo convert(OrderForm orderForm) {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setClientId(orderForm.getClientId());
        orderPojo.setCustomerId(orderForm.getCustomerId());
        orderPojo.setChannelId(orderForm.getChannelId());
        if (orderForm.getChannelOrderId() != null && !orderForm.getChannelOrderId().isEmpty())
            orderPojo.setChannelOrderId(CommonsHelper.normalize(orderForm.getChannelOrderId()));
        return orderPojo;
    }

    public static OrderXmlForm convert(OrderPojo p, List<OrderItemXmlForm> orderItemXmlForms) {
        OrderXmlForm orderXmlForm = new OrderXmlForm(p);
        orderXmlForm.setItems(orderItemXmlForms);
        Double totalCost = 0D;
        for (OrderItemXmlForm oi : orderItemXmlForms) {
            totalCost += Double.parseDouble(oi.getTotalSellingPrice());
        }
        orderXmlForm.setTotal(CommonsHelper.doubleToString(totalCost));
        return orderXmlForm;
    }

    public static String generateXML(Long id, OrderXmlForm orderXmlForm) throws ApiException {
        String fname = "./xml_data/order_" + id.toString() + ".xml";
        File file = new File(fname);
        try {
            JAXBContext context = JAXBContext.newInstance(OrderXmlForm.class);
            Marshaller jaxbMarshaller = context.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(orderXmlForm, file);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
        return file.getAbsolutePath();
    }

    public static List<String> generatePaths(String fname) {
        List<String> strings = new ArrayList<String>();
        strings.add(
                "/home/abhk943/Documents/increff/toy_assure/assure/src/main/resources/OrderPdfModel.xsl");
        strings.add(fname.substring(0, fname.lastIndexOf('/')) + "/generated_pdf");
        fname = fname.substring(fname.lastIndexOf('/') + 1);
        fname = fname.substring(0, fname.lastIndexOf(".")) + ".pdf";
        strings.add(fname);
        return strings;
    }

    public static void generatePdf(String fname, List<String> paths) throws ApiException {
        try {
            File xmlfile = new File(fname);
            File xsltfile = new File(paths.get(0));
            File pdfDir = new File(paths.get(1));
            pdfDir.mkdirs();
            File pdfFile = new File(pdfDir, paths.get(2));
            final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            OutputStream out = new FileOutputStream(pdfFile);
            out = new java.io.BufferedOutputStream(out);

            Fop fop;
            fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));
            Source src = new StreamSource(xmlfile);
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    public static byte[] getPdf(Long id) throws ApiException {
        String filepath =
                "/home/abhk943/Documents/increff/toy_assure/assure/xml_data/generated_pdf/order_"
                        + id.toString() + ".pdf";
        try {
            byte[] inFileBytes = Files.readAllBytes(Paths.get(filepath));
            byte[] encoded = org.apache.commons.codec.binary.Base64.encodeBase64(inFileBytes);
            return encoded;
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

}
