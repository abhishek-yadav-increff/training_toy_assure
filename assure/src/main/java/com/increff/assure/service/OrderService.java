package com.increff.assure.service;

import java.util.List;
import com.increff.assure.dao.OrderDao;
import com.increff.assure.enums.OrderEnum;
import com.increff.assure.model.OrderItemData;
import com.increff.assure.pojo.OrderItemPojo;
import com.increff.assure.pojo.OrderPojo;
import com.increff.commons.model.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderDao dao;

    @Autowired
    private OrderItemService orderItemService;

    @Transactional(rollbackFor = ApiException.class)
    public void add(OrderPojo p) throws ApiException {

        validateAdd(p);
        dao.insert(p);
    }

    private void validateAdd(OrderPojo p) throws ApiException {
        if (p.getChannelOrderId() == null || p.getChannelOrderId().isEmpty())
            throw new ApiException("Channel Order ID can not be empty!!");
        if (p.getClientId() == null)
            throw new ApiException("Client ID can not be empty!!");
        if (p.getCustomerId() == null)
            throw new ApiException("Customer ID can not be empty!!");
        if (p.getChannelId() == null)
            throw new ApiException("Channel ID can not be empty!!");
        if (getByChannelOrderId(p.getChannelOrderId()) != null)
            throw new ApiException("Channel Order ID already exists!!");
    }

    @Transactional(readOnly = true)
    public OrderPojo get(Long id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(readOnly = true)
    public OrderPojo getByChannelOrderId(String id) throws ApiException {
        return dao.selectByChannelOrderId(id);
    }

    @Transactional(readOnly = true)
    public List<OrderPojo> getAll() throws ApiException {
        List<OrderPojo> orderPojos = dao.selectAll();
        if (orderPojos == null) {
            throw new ApiException("No Order Category Pair in database!");
        }
        return orderPojos;
    }

    @Transactional(readOnly = true)
    public OrderPojo getCheck(Long id) throws ApiException {
        OrderPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Order with given ID does not exist, id: " + id);
        }
        return p;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(Long id, OrderEnum orderEnum) throws ApiException {
        OrderPojo ex = getCheck(id);
        ex.setStatus(orderEnum);
        dao.update(ex);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void allocate(Long id) throws ApiException {
        if (get(id).getStatus() != OrderEnum.CREATED)
            throw new ApiException("Order only with status created can be allocated!!");
        System.out.println("Before allocating: " + id);
        Boolean isAllocated = orderItemService.allocate(id);
        System.out.println("After allocating: " + id);
        System.out.println("Complete allocation: " + isAllocated);
        if (isAllocated)
            update(id, OrderEnum.ALLOCATED);
    }

    public void addOrderItem(OrderItemPojo orderItemPojo) throws ApiException {
        orderItemService.add(orderItemPojo);
    }

    public void generateInvoice(Long id) throws ApiException {
        if (get(id).getStatus() != OrderEnum.ALLOCATED)
            throw new ApiException("Order only with status ALLOCATED can be allocated!!");
        orderItemService.generateInvoice(id);
        update(id, OrderEnum.FULFILLED);
    }

}
