package com.increff.assure.service;

import java.util.ArrayList;
import java.util.List;
import com.increff.assure.dao.ChannelListingDao;
import com.increff.assure.pojo.ChannelListingPojo;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.ErrorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChannelListingService {

    @Autowired
    private ChannelListingDao dao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(List<ChannelListingPojo> p) throws ApiException {
        System.out.print("in add()" + p.toString());
        validateAdd(p);
        for (ChannelListingPojo chLiPojo : p) {
            dao.insert(chLiPojo);
        }
    }

    private void validateAdd(List<ChannelListingPojo> p) throws ApiException {
        List<ErrorData> errorDatas = new ArrayList<>();
        for (ChannelListingPojo chLiPojo : p) {
            try {
                ChannelListingPojo chp = getByClientIdChanneIdGlobalSkuId(chLiPojo);
                if (chp != null)
                    throw new ApiException(
                            "Listing with combination Global SKU ID, Client ID, ChannelId already exists!!");
                ChannelListingPojo chp2 = getByClientIdChanneIdChannelSkuId(chLiPojo);
                if (chp2 != null)
                    throw new ApiException(
                            "Listing with combination Channel SKU ID, Client ID, ChannelId already exists!!");
            } catch (Exception e) {
                errorDatas.add(new ErrorData("error", e.getMessage()));
            }
        }
        if (!errorDatas.isEmpty())
            throw new ApiException("Failed validating listings!!", errorDatas);
    }

    private ChannelListingPojo getByClientIdChanneIdChannelSkuId(ChannelListingPojo p) {
        return dao.getByClientIdChanneIdGlobalSkuId(p.getClientId(), p.getChannelId(),
                p.getGlobalSkuId());
    }

    private ChannelListingPojo getByClientIdChanneIdGlobalSkuId(ChannelListingPojo p) {
        return dao.getByClientIdChanneIdGlobalSkuId(p.getClientId(), p.getChannelId(),
                p.getChannelSkuId());
    }

    @Transactional(readOnly = true)
    public ChannelListingPojo get(Long id) throws ApiException {
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
    public ChannelListingPojo getCheck(Long id) throws ApiException {
        ChannelListingPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("ChannelListing with given ID does not exist, id: " + id);
        }
        return p;
    }

}
