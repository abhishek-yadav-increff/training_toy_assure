package com.increff.assure.dto;

import static org.junit.Assert.assertEquals;
import com.increff.assure.model.BinForm;
import com.increff.assure.model.BinIndexRange;
import com.increff.assure.service.AbstractUnitTest;
import com.increff.assure.utils.BinUtils;
import com.increff.commons.model.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * BinDtoTest
 */
public class BinDtoTest extends AbstractUnitTest {
    @Autowired
    private BinDto binDto;

    @Test
    public void testAddBin() throws ApiException {
        BinIndexRange bir = binDto.getRange();

        BinForm binForm = BinUtils.getBinForm(Long.valueOf(2));
        binDto.add(binForm);

        BinIndexRange bir2 = binDto.getRange();

        assertEquals(Long.valueOf(bir.getBgIndex() + Long.valueOf(2)), bir2.getBgIndex());
    }

}
