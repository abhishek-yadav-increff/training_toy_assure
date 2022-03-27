package com.increff.assure.dto;

import static org.junit.Assert.assertEquals;
import java.util.List;
import com.increff.assure.service.AbstractUnitTest;
import com.increff.assure.utils.ChannelUtils;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.ChannelData;
import com.increff.commons.model.ChannelForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ChannelDtoTest
 */
public class ChannelDtoTest extends AbstractUnitTest {

    @Autowired
    private ChannelDto channelDto;


    @Test
    public void testAddSuccess() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm(" naMe   ", " seLf ");
        channelDto.add(channelForm);

        List<ChannelData> channelDatas = channelDto.getAll();
        assertEquals(1, channelDatas.size());

        ChannelData channelData = channelDto.get(channelDatas.get(0).getId());
        assertEquals(channelData.getName(), "name");
        assertEquals(channelData.getInvoiceType(), "SELF");
    }

    @Test(expected = ApiException.class)
    public void testAddNullName() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm(null, "  seLf  ");
        channelDto.add(channelForm);
    }

    @Test(expected = ApiException.class)
    public void testAddNullChannelType() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm("name", null);
        channelDto.add(channelForm);
    }

    @Test(expected = ApiException.class)
    public void testAddEmptyName() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm("", "  seLf  ");
        channelDto.add(channelForm);
    }

    @Test(expected = ApiException.class)
    public void testAddEmptyChannelType() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm("name", "");
        channelDto.add(channelForm);
    }

    @Test(expected = ApiException.class)
    public void testAddInvalidChannelType() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm("name", " clienT ");
        channelDto.add(channelForm);
    }

    @Test(expected = ApiException.class)
    public void testAddDuplicateName() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm(" naMe ", " seLf ");
        channelDto.add(channelForm);
        channelForm = ChannelUtils.getChannelForm(" naMe ", " ChannEl ");
        channelDto.add(channelForm);
    }

    @Test
    public void testGetSuccess() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm(" naMe   ", " seLf ");
        channelDto.add(channelForm);

        List<ChannelData> channelDatas = channelDto.getAll();
        assertEquals(1, channelDatas.size());

        ChannelData channelData = channelDto.get(channelDatas.get(0).getId());
        assertEquals(channelData.getName(), "name");
        assertEquals(channelData.getInvoiceType(), "SELF");
    }

    @Test
    public void testGetAllSuccess() throws ApiException {
        ChannelForm channelForm = ChannelUtils.getChannelForm(" naMe   ", " seLf ");
        channelDto.add(channelForm);
        channelForm = ChannelUtils.getChannelForm(" naMeE   ", " channEl ");
        channelDto.add(channelForm);

        List<ChannelData> channelDatas = channelDto.getAll();
        assertEquals(2, channelDatas.size());
    }
}
