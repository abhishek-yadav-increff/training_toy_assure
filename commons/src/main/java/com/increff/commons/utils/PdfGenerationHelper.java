package com.increff.commons.utils;

import com.increff.commons.model.OrderXmlForm;
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
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import com.increff.commons.model.ApiException;
import org.apache.fop.apps.MimeConstants;

/**
 * PdfGenerationHelper
 */
public class PdfGenerationHelper {
    public static String generateXML(String id, OrderXmlForm orderXmlForm) throws ApiException {
        String fname = "./xml_data/order_" + id + ".xml";
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

    public static String generateXML(Long id, OrderXmlForm orderXmlForm) throws ApiException {
        return generateXML(id.toString(), orderXmlForm);
        // String fname = "./xml_data/order_" + id.toString() + ".xml";
        // File file = new File(fname);
        // try {
        // JAXBContext context = JAXBContext.newInstance(OrderXmlForm.class);
        // Marshaller jaxbMarshaller = context.createMarshaller();
        // jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        // jaxbMarshaller.marshal(orderXmlForm, file);
        // } catch (Exception e) {
        // throw new ApiException(e.getMessage());
        // }
        // return file.getAbsolutePath();
    }

    public static List<String> generatePaths(String fname, String xlsModelPath) {
        List<String> strings = new ArrayList<String>();
        strings.add(xlsModelPath);
        strings.add(fname.substring(0, fname.lastIndexOf('/')) + "/generated_pdf");
        fname = fname.substring(fname.lastIndexOf('/') + 1);
        fname = fname.substring(0, fname.lastIndexOf(".")) + ".pdf";
        strings.add(fname);
        return strings;
    }

    public static byte[] getPdf(Long id, String pdfFolder) throws ApiException {
        return getPdf(id.toString(), pdfFolder);
    }

    public static byte[] getPdf(String id, String pdfFolder) throws ApiException {
        String filepath = pdfFolder + "order_" + id + ".pdf";
        try {
            byte[] inFileBytes = Files.readAllBytes(Paths.get(filepath));
            byte[] encoded = org.apache.commons.codec.binary.Base64.encodeBase64(inFileBytes);
            return encoded;
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    public static void generatePdf(String fname, List<String> paths) throws ApiException {
        OutputStream out = null;
        try {
            File xmlfile = new File(fname);
            File xsltfile = new File(paths.get(0));
            File pdfDir = new File(paths.get(1));
            pdfDir.mkdirs();
            File pdfFile = new File(pdfDir, paths.get(2));
            final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            out = new FileOutputStream(pdfFile);
            out = new java.io.BufferedOutputStream(out);

            Fop fop;
            fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));
            Source src = new StreamSource(xmlfile);
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
            out.close();
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }
}
