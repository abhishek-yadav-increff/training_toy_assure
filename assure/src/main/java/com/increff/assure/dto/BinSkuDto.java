package com.increff.assure.dto;

import java.util.ArrayList;
import java.util.List;
import com.increff.assure.dto.helper.BinSkuDtoHelper;
import com.increff.assure.model.BinSkuData;
import com.increff.assure.model.BinSkuForm;
import com.increff.assure.pojo.BinSkuPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.BinSkuService;
import com.increff.assure.service.ProductService;
import com.increff.common.model.ApiException;
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
        for (BinSkuForm binSkuForm : binSkuForms) {
            ProductPojo productPojo = productService
                    .getClientIdClientSkuId(binSkuForm.getClientId(), binSkuForm.getClientSkuId());
            Long globalSkuId = null;
            if (productPojo != null)
                globalSkuId = productPojo.getGlobalSkuId();
            BinSkuPojo binSkuPojo = BinSkuDtoHelper.convert(binSkuForm, globalSkuId);
            binSkuPojos.add(binSkuPojo);
        }
        binSkuService.add(binSkuPojos);

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
