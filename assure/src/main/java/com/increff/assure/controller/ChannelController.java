package com.increff.assure.controller;

import java.util.List;
import com.increff.assure.dto.ChannelDto;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.ChannelData;
import com.increff.commons.model.ChannelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class ChannelController {

    @Autowired
    private ChannelDto channelDto;

    @ApiOperation(value = "Adds a Channel")
    @RequestMapping(path = "/api/channel", method = RequestMethod.POST)
    public void add(@RequestBody ChannelForm form) throws ApiException {
        channelDto.add(form);
    }

    // @ApiOperation(value = "Gets a Channel by ID")
    // @RequestMapping(path = "/api/channel/{id}", method = RequestMethod.GET)
    // public ChannelData get(@PathVariable Long id) throws ApiException {
    // return channelDto.get(id);
    // }

    @ApiOperation(value = "Search a Channel by ID and Name")
    @RequestMapping(path = "/api/channel/search/", method = RequestMethod.GET)
    public List<ChannelData> get(@RequestParam(value = "term", required = false) String query,
            @RequestParam(value = "type", required = true) String type) throws ApiException {
        return channelDto.getByQuery(query);
    }

    @ApiOperation(value = "Search a Channel by ID and Name")
    @RequestMapping(path = "/api/channel/search/external", method = RequestMethod.GET)
    public List<ChannelData> getForChannel(
            @RequestParam(value = "term", required = false) String query,
            @RequestParam(value = "type", required = true) String type) throws ApiException {
        return channelDto.getByQueryForChannel(query);
    }

    @ApiOperation(value = "Gets list of all Channels")
    @RequestMapping(path = "/api/channel", method = RequestMethod.GET)
    public List<ChannelData> getAll() throws ApiException {
        return channelDto.getAll();
    }

}
