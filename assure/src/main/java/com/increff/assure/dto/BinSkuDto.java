package com.increff.assure.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.increff.assure.dto.helper.BinSkuDtoHelper;
import com.increff.assure.dto.helper.CommonsHelper;
import com.increff.assure.model.BinSkuData;
import com.increff.assure.model.BinSkuForm;
import com.increff.assure.pojo.BinSkuPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.BinSkuService;
import com.increff.assure.service.ProductService;
import com.increff.commons.model.ApiException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BinSkuDto
 */
@Service
public class BinSkuDto {

    @Autowired
    private BinSkuService binSkuService;

    @Autowired
    private ProductService productService;

    private static final Logger LOGGER = Logger.getLogger(BinSkuDto.class);

    public void add(List<BinSkuForm> binSkuForms) throws ApiException {
        LOGGER.info("In add()");
        LOGGER.info("Form data received: " + binSkuForms.toString());

        List<BinSkuPojo> binSkuPojos = new ArrayList<BinSkuPojo>();
        validateForms(binSkuForms);
        for (BinSkuForm binSkuForm : binSkuForms) {
            ProductPojo productPojo = productService.getClientIdClientSkuId(
                    binSkuForm.getClientId(), binSkuForm.getClientSkuId().trim());
            Long globalSkuId = null;
            if (productPojo != null)
                globalSkuId = productPojo.getGlobalSkuId();
            BinSkuPojo binSkuPojo = BinSkuDtoHelper.convert(binSkuForm, globalSkuId);
            binSkuPojos.add(binSkuPojo);
        }

        binSkuService.add(binSkuPojos);

    }



    private void validateForms(List<BinSkuForm> binSkuForms) throws ApiException {
        for (BinSkuForm bsk : binSkuForms)
            validateForm(bsk);
        List<BinSkuForm> binSkuForms2 =
                binSkuForms.stream().filter(CommonsHelper.distinctByKey(BinSkuForm::getClientId))
                        .collect(Collectors.toList());
        if (binSkuForms2.size() != 1)
            throw new ApiException("Multiple Client ID not allowed!!");
        final List<BinSkuForm> binSkuForms3 = CommonsHelper.distinctList(binSkuForms,
                BinSkuForm::getBinId, BinSkuForm::getClientSkuId);
        if (binSkuForms3.size() != binSkuForms.size())
            throw new ApiException("Client Sku Id must be unique!!");

    }

    private void validateForm(BinSkuForm bsk) throws ApiException {
        if (bsk.getBinId() == null)
            throw new ApiException("Bin ID can not be empty!!");
        if (bsk.getClientId() == null)
            throw new ApiException("Client ID can not be empty!!");
        if (bsk.getClientSkuId() == null || bsk.getClientSkuId().isEmpty())
            throw new ApiException("Client Sku ID can not be empty!!");
        if (bsk.getQuantity() == null)
            throw new ApiException("Quantity can not be empty!!");
    }

    public BinSkuData get(Long id) throws ApiException {
        BinSkuPojo binSkuPojo = binSkuService.get(id);
        return BinSkuDtoHelper.convert(binSkuPojo);
    }

    public List<BinSkuData> getAll() throws ApiException {
        List<BinSkuPojo> binSkuPojos = binSkuService.getAll();
        return BinSkuDtoHelper.convert(binSkuPojos);
    }

    public List<BinSkuData> getByGlobalSkuId(Long globalSkuId) throws ApiException {
        List<BinSkuPojo> binSkuPojos = binSkuService.getByGlobalSkuId(globalSkuId);
        return BinSkuDtoHelper.convert(binSkuPojos);
    }

}
