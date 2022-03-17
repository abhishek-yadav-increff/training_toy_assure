package com.increff.assure.service;

import java.util.List;
import com.increff.assure.dao.ChannelListingDao;
import com.increff.assure.pojo.ChannelListingPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChannelListingService {

    @Autowired
    private ChannelListingDao dao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(ChannelListingPojo p) throws ApiException {
        dao.insert(p);
    }

    @Transactional(readOnly = true)
    public ChannelListingPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(readOnly = true)
    public List<ChannelListingPojo> getAll() throws ApiException {
        List<ChannelListingPojo> channelListingPojos = dao.selectAll();
        if (channelListingPojos == null) {
            throw new ApiException("No ChannelListing Category Pair in database!");
        }
        return channelListingPojos;
    }

    @Transactional(readOnly = true)
    public ChannelListingPojo getCheck(int id) throws ApiException {
        ChannelListingPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("ChannelListing with given ID does not exist, id: " + id);
        }
        return p;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, ChannelListingPojo p) throws ApiException {
        ChannelListingPojo ex = getCheck(id);
        dao.update(ex);
    }

}
