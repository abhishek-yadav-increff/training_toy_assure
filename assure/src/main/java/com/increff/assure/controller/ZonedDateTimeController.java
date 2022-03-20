package com.increff.assure.controller;

import java.util.List;
import com.increff.assure.dto.ZonedDateTimeDto;
import com.increff.assure.model.ZonedDateTimeData;
import com.increff.assure.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class ZonedDateTimeController {

    @Autowired
    private ZonedDateTimeDto zonedDateTimeDto;

    @ApiOperation(value = "Gets all ZonedDateTime")
    @RequestMapping(path = "/api/zoneddatetime/", method = RequestMethod.GET)
    public List<ZonedDateTimeData> get() throws ApiException {
        return zonedDateTimeDto.get();
    }

    @ApiOperation(value = "Gets all ZonedDateTime")
    @RequestMapping(path = "/api/zoneddatetime/{zoneDateTime}", method = RequestMethod.GET)
    public String get(@PathVariable String zoneDateTime) throws ApiException {
        System.out.print(zoneDateTime);
        return zonedDateTimeDto.getFor(zoneDateTime);
    }

    @ApiOperation(value = "Search a ZonedDateTime by ID and Name")
    @RequestMapping(path = "/api/zoneddatetime/search/", method = RequestMethod.GET)
    public List<ZonedDateTimeData> get(@RequestParam(value = "term", required = false) String query,
            @RequestParam(value = "type", required = true) String type) throws ApiException {
        return zonedDateTimeDto.getByQuery(query);
    }
}
