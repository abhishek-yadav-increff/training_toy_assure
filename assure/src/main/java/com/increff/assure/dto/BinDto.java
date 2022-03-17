package com.increff.assure.dto;

import java.util.List;
import com.increff.assure.dto.helper.BinDtoHelper;
import com.increff.assure.model.BinData;
import com.increff.assure.model.BinForm;
import com.increff.assure.pojo.BinPojo;
import com.increff.assure.service.ApiException;
import com.increff.assure.service.BinService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BinDto
 */
@Service
public class BinDto {

    @Autowired
    private BinService binService;

    private static final Logger LOGGER = Logger.getLogger(BinDto.class);

    public void add(BinForm binForm) throws ApiException {
        LOGGER.info("In BinService:add()");
        LOGGER.info("Form data received: " + binForm.toString());
        binService.add(binForm);
    }

    public BinData get(Long id) throws ApiException {
        BinPojo binPojo = binService.get(id);
        return BinDtoHelper.convert(binPojo);
    }

    public List<BinData> getAll() throws ApiException {
        List<BinPojo> binPojos = binService.getAll();
        return BinDtoHelper.convert(binPojos);
    }

}
