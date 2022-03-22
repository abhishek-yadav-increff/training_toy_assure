package com.increff.assure.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import com.increff.commons.model.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ZonedDateTimeService {

    @Transactional(readOnly = true)
    public Map<String, String> get() throws ApiException {
        return getAllZoneIdsAndItsOffSet();
    }

    private Map<String, String> getAllZoneIdsAndItsOffSet() {
        Map<String, String> result = new HashMap<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        for (String zoneId : ZoneId.getAvailableZoneIds()) {
            ZoneId id = ZoneId.of(zoneId);
            // LocalDateTime -> ZonedDateTime
            ZonedDateTime zonedDateTime = localDateTime.atZone(id);
            // ZonedDateTime -> ZoneOffset
            ZoneOffset zoneOffset = zonedDateTime.getOffset();
            // replace Z to +00:00
            String offset = zoneOffset.getId().replaceAll("Z", "+00:00");
            result.put(id.toString(), offset);
        }
        return result;
    }
}
