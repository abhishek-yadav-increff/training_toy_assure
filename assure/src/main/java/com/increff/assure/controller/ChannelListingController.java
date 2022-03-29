package com.increff.assure.controller;

import java.util.List;
import com.increff.assure.dto.ChannelListingDto;
import com.increff.assure.model.ChannelListingData;
import com.increff.assure.model.ChannelListingForm;
import com.increff.commons.model.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class ChannelListingController {


    @Autowired
    private ChannelListingDto channelListingDto;

    @ApiOperation(value = "Adds a ChannelListing")
    @RequestMapping(path = "/api/channellisting", method = RequestMethod.POST)
    public void add(@RequestBody List<ChannelListingForm> forms) throws ApiException {
        channelListingDto.add(forms);
    }

    // @ApiOperation(value = "Gets a ChannelListing by ID")
    // @RequestMapping(path = "/api/channellisting/{id}", method = RequestMethod.GET)
    // public ChannelListingData get(@PathVariable Long id) throws ApiException {
    // return channelListingDto.get(id);
    // }

    @ApiOperation(value = "Gets list of all ChannelListings")
    @RequestMapping(path = "/api/channellisting", method = RequestMethod.GET)
    public List<ChannelListingData> getAll() throws ApiException {
        return channelListingDto.getAll();
    }

}
