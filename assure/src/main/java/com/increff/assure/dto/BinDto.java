package com.increff.assure.dto;

import com.increff.assure.dto.helper.BinDtoHelper;
import com.increff.assure.model.BinForm;
import com.increff.assure.model.BinIndexRange;
import com.increff.assure.service.BinService;
import com.increff.common.model.ApiException;
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

    public BinIndexRange add(BinForm binForm) throws ApiException {
        LOGGER.info("In add()");
        LOGGER.info("Form data received: " + binForm.toString());
        Long smIndex = binService.add(binForm);
        return BinDtoHelper.convertToRange(smIndex, binForm.getBinSize());
    }

    // public BinData get(Long id) throws ApiException {
    // BinPojo binPojo = binService.get(id);
    // return BinDtoHelper.convert(binPojo);
    // }

    // public List<BinData> getAll() throws ApiException {
    // List<BinPojo> binPojos = binService.getAll();
    // return BinDtoHelper.convert(binPojos);
    // }

}
