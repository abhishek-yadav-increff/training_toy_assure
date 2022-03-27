package com.increff.assure.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import com.increff.assure.dto.helper.BinDtoHelper;
import com.increff.assure.model.BinForm;
import com.increff.assure.model.BinIndexRange;
import com.increff.assure.pojo.BinPojo;
import com.increff.assure.service.BinService;
import com.increff.commons.model.ApiException;
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

    public BinIndexRange getRange() throws ApiException {
        List<BinPojo> binPojos = new ArrayList<BinPojo>();
        binPojos = binService.getAll();
        Long minIndex = 10000L;
        Long maxIndex = 10000L;

        if (binPojos != null) {
            try {
                minIndex = binPojos.stream().min(Comparator.comparingLong(BinPojo::getBinId)).get()
                        .getBinId();
                maxIndex = binPojos.stream().max(Comparator.comparingLong(BinPojo::getBinId)).get()
                        .getBinId();
            } catch (NoSuchElementException ex) {
            }
        }
        BinIndexRange bir = new BinIndexRange(minIndex, maxIndex);
        return bir;
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
