package com.increff.channel.client.assureClient;

import java.util.Arrays;
import java.util.List;
import com.increff.commons.model.ChannelData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * AssureClient
 */
@Service
public class ChannelAssureClient {

    @Autowired
    private RestTemplate restTemplate;

    public List<ChannelData> getChannelsByQuery(String query) {
        ResponseEntity<ChannelData[]> response;
        if (query == null || query.isEmpty())
            response = restTemplate.getForEntity(
                    "http://localhost:9000/assure/api/channel/search/external/?type=query",
                    ChannelData[].class);
        else
            response = restTemplate
                    .getForEntity("http://localhost:9000/assure/api/channel/search/external/?term="
                            + query + "&type=query", ChannelData[].class);
        List<ChannelData> channelDatas = Arrays.asList(response.getBody());
        return channelDatas;
    }
}
