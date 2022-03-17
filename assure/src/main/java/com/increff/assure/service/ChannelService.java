package com.increff.assure.service;

import java.util.List;
import java.util.stream.Collectors;
import com.increff.assure.dao.ChannelDao;
import com.increff.assure.dto.helper.CommonsHelper;
import com.increff.assure.pojo.ChannelPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChannelService {
    private static final Logger LOGGER = Logger.getLogger(ChannelService.class);

    @Autowired
    private ChannelDao dao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(ChannelPojo p) throws ApiException {
        dao.insert(p);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public ChannelPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public List<ChannelPojo> getAll() throws ApiException {
        List<ChannelPojo> channelPojos = dao.selectAll();
        if (channelPojos == null) {
            throw new ApiException("No Channel Category Pair in database!");
        }
        return channelPojos;
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public ChannelPojo getCheck(int id) throws ApiException {
        ChannelPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Channel with given ID does not exist, id: " + id);
        }
        return p;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, ChannelPojo p) throws ApiException {
        ChannelPojo ex = getCheck(id);
        ex.setName(p.getName());
        ex.setInvoiceType(p.getInvoiceType());
        dao.update(ex);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    private List<ChannelPojo> queryByName(String name) throws ApiException {
        List<ChannelPojo> channelPojos = dao.queryByName(name);
        if (channelPojos == null) {
            return null;
        }
        return channelPojos;
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    private List<ChannelPojo> queryById(Long id) throws ApiException {
        List<ChannelPojo> channelPojos = getAll();
        if (channelPojos == null) {
            throw new ApiException("Channels don't exist!!");
        }
        List<ChannelPojo> channelPojos2 =
                channelPojos.stream().filter(c -> c.getId().toString().matches(".*" + id + ".*"))
                        .collect(Collectors.toList());
        return channelPojos2;
    }

    public List<ChannelPojo> getByQuery(String query) throws ApiException {
        LOGGER.info("Inside getByQuery");
        LOGGER.info("Query: " + query);
        if (query == null || query.isEmpty()) {
            LOGGER.info("Returning all");
            return dao.selectAll();
        } else if (CommonsHelper.isNumeric(query)) {
            LOGGER.info("Returning matched by id");
            return queryById(Long.parseLong(query));
        } else {
            LOGGER.info("Returning matched by name");
            List<ChannelPojo> channelPojos = queryByName(query);
            LOGGER.info("Size of return: " + channelPojos.size());
            return channelPojos;
        }

    }
}
