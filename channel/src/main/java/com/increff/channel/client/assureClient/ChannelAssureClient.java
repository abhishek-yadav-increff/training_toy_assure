package com.increff.channel.client.assureClient;

import java.util.Arrays;
import java.util.List;
import com.increff.channel.model.ChannelData;
import com.increff.channel.model.ChannelForm;
import com.increff.commons.model.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * AssureClient
 */
public class ChannelAssureClient {

    private static RestTemplate restTemplate;

    static {
        restTemplate = new RestTemplate();
    }

    public static List<ChannelData> getChannels() throws ApiException {
        ResponseEntity<ChannelData[]> response = restTemplate
                .getForEntity("http://localhost:9000/assure/api/channel", ChannelData[].class);
        List<ChannelData> channelDatas = Arrays.asList(response.getBody());
        return channelDatas;
    }

    public static void addChannel(ChannelForm channelForm) {
        restTemplate.postForObject("http://localhost:9000/assure/api/channel", channelForm,
                ResponseEntity.class);
    }

    public static ChannelData getChannel(Long id) {
        ResponseEntity<ChannelData> response = restTemplate
                .getForEntity("http://localhost:9000/assure/api/channel/" + id, ChannelData.class);
        return response.getBody();
    }

    public static List<ChannelData> getChannelsByQuery(String query) {
        ResponseEntity<ChannelData[]> response;
        if (query == null || query.isEmpty())
            response = restTemplate.getForEntity(
                    "http://localhost:9000/assure/api/channel/search/?type=query",
                    ChannelData[].class);
        else
            response = restTemplate
                    .getForEntity("http://localhost:9000/assure/api/channel/search/?term=" + query
                            + "&type=query", ChannelData[].class);
        List<ChannelData> channelDatas = Arrays.asList(response.getBody());
        return channelDatas;
    }
}
