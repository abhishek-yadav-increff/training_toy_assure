package com.increff.assure.service;

import java.util.List;
import com.increff.assure.dao.OrderItemDao;
import com.increff.assure.pojo.OrderItemPojo;
import com.increff.commons.model.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao dao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(OrderItemPojo p) throws ApiException {
        dao.insert(p);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public OrderItemPojo get(Long id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public List<OrderItemPojo> getAll() throws ApiException {
        List<OrderItemPojo> orderItemPojos = dao.selectAll();
        return orderItemPojos;
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public OrderItemPojo getCheck(Long id) throws ApiException {
        OrderItemPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("OrderItem with given ID does not exist, id: " + id);
        }
        return p;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(Long id, OrderItemPojo p) throws ApiException {
        OrderItemPojo ex = getCheck(id);
        dao.update(ex);
    }
}
