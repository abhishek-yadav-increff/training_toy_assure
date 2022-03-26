package com.increff.channel.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.increff.commons.model.ChannelData;
import com.increff.channel.dto.ChannelDto;
import com.increff.commons.model.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @ApiOperation(value = "Search a Channel by ID and Name")
    @RequestMapping(path = "/api/channel/search/", method = RequestMethod.GET)
    public List<ChannelData> get(@RequestParam(value = "term", required = false) String query,
            @RequestParam(value = "type", required = true) String type, HttpServletRequest res)
            throws ApiException {
        System.out.println(res.getRequestURI());
        return channelDto.getByQuery(query);
    }


}
