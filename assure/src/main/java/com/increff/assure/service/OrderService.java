package com.increff.assure.service;

import java.util.List;
import com.increff.assure.dao.OrderDao;
import com.increff.assure.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderDao dao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(OrderPojo p) throws ApiException {
        dao.insert(p);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public OrderPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public List<OrderPojo> getAll() throws ApiException {
        List<OrderPojo> orderPojos = dao.selectAll();
        if (orderPojos == null) {
            throw new ApiException("No Order Category Pair in database!");
        }
        return orderPojos;
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public OrderPojo getCheck(int id) throws ApiException {
        OrderPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Order with given ID does not exist, id: " + id);
        }
        return p;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, OrderPojo p) throws ApiException {
        OrderPojo ex = getCheck(id);
        dao.update(ex);
    }

    public void add(List<OrderPojo> orderPojos) {
        for (OrderPojo p : orderPojos) {
            dao.insert(p);
        }
    }

}
