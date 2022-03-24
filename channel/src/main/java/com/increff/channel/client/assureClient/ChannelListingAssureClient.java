package com.increff.channel.client.assureClient;

import java.util.Arrays;
import java.util.List;
import com.increff.commons.model.ChannelListingData;
import com.increff.commons.model.ChannelListingForm;
import com.increff.commons.model.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * AssureClient
 */
@Service
public class ChannelListingAssureClient {

    @Autowired
    private RestTemplate restTemplate;



    public List<ChannelListingData> getChannelListings() throws ApiException {
        ResponseEntity<ChannelListingData[]> response = restTemplate.getForEntity(
                "http://localhost:9000/assure/api/channellisting", ChannelListingData[].class);
        List<ChannelListingData> channelDatas = Arrays.asList(response.getBody());
        return channelDatas;
    }

    public void addChannelListing(List<ChannelListingForm> channelForms) {
        restTemplate.postForObject("http://localhost:9000/assure/api/channellisting", channelForms,
                ResponseEntity.class);
    }

    public ChannelListingData getChannelListing(Long id) {
        ResponseEntity<ChannelListingData> response = restTemplate.getForEntity(
                "http://localhost:9000/assure/api/channellisting/" + id, ChannelListingData.class);
        return response.getBody();
    }
}
