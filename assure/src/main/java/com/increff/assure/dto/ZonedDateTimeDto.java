package com.increff.assure.dto;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import com.increff.assure.dto.helper.CommonsHelper;
import com.increff.assure.dto.helper.ZonedDateTimeDtoHelper;
import com.increff.assure.model.ZonedDateTimeData;
import com.increff.assure.service.ZonedDateTimeService;
import com.increff.common.model.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ZonedDateTimeDto
 */
@Service
public class ZonedDateTimeDto {

    @Autowired
    private ZonedDateTimeService zonedDateTimeService;

    public List<ZonedDateTimeData> get() throws ApiException {
        Map<String, String> zonedDateTimeMap = zonedDateTimeService.get();
        return ZonedDateTimeDtoHelper.convert(zonedDateTimeMap);
    }

    public List<ZonedDateTimeData> getByQuery(String query) throws ApiException {
        String queryString = CommonsHelper.normalize(query);
        if (queryString == null || queryString.isEmpty()) {
            return get();
        }
        List<ZonedDateTimeData> zonedDateTimeDatas = get();
        List<ZonedDateTimeData> zonedDateTimeDatas2 = zonedDateTimeDatas.stream()
                .filter(c -> c.getZoneDateTime().toLowerCase().matches(".*" + queryString + ".*")
                        || c.getOffset().toLowerCase().matches(".*" + queryString + ".*"))
                .collect(Collectors.toList());
        return zonedDateTimeDatas2;
    }

    public String getFor(String zoneDateTime) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-uuuu hh:mm a", Locale.ENGLISH);
        Instant instant = Instant.now();
        ZonedDateTime zdtUtc = instant.atZone(ZoneId.of(zoneDateTime));
        return zdtUtc.format(dtf);
    }


}
