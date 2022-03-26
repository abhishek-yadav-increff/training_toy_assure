package com.increff.assure.service;

import java.util.List;
import com.increff.assure.dao.OrderItemDao;
import com.increff.assure.pojo.BinSkuPojo;
import com.increff.assure.pojo.InventoryPojo;
import com.increff.assure.pojo.OrderItemPojo;
import com.increff.commons.model.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao dao;

    @Autowired
    private BinSkuService binSkuService;

    @Autowired
    private InventoryService inventoryService;

    @Transactional(rollbackFor = ApiException.class)
    public void add(OrderItemPojo p) throws ApiException {
        dao.insert(p);
    }

    @Transactional(readOnly = true)
    public OrderItemPojo get(Long id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(readOnly = true)
    public List<OrderItemPojo> getByOrderId(Long orderId) throws ApiException {
        return dao.getByOrderId(orderId);
    }

    @Transactional(readOnly = true)
    public List<OrderItemPojo> getAll() throws ApiException {
        List<OrderItemPojo> orderItemPojos = dao.selectAll();
        return orderItemPojos;
    }


    @Transactional(readOnly = true)
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

    @Transactional(rollbackFor = ApiException.class)
    public Boolean allocate(Long id) throws ApiException {
        Boolean isAllocated = true;
        List<OrderItemPojo> orderItemPojos = getByOrderId(id);
        // System.out.println("Will allocate following: " + orderItemPojos);
        if (orderItemPojos.size() == 0)
            throw new ApiException("Can not allocate empty order!!");
        for (OrderItemPojo p : orderItemPojos) {
            allocate(p);
            if (p.getOrderedQuantity() != 0)
                isAllocated = false;
        }
        return isAllocated;
    }

    @Transactional(rollbackFor = ApiException.class)
    private void allocate(OrderItemPojo p) throws ApiException {
        List<BinSkuPojo> binSkuPojos = binSkuService.getByGlobalSkuId(p.getGlobalSkuId());
        for (BinSkuPojo b : binSkuPojos) {
            Long reduceQuantity = Math.min(p.getOrderedQuantity(), b.getQuantity());

            // remove from binskus
            // remove from inventory called from binskuservice
            // System.out.println("Before binsku, inventory quantity reduced: " + b);
            b.setQuantity(b.getQuantity() - reduceQuantity);
            binSkuService.update(b.getId(), b);
            // System.out.println("After binsku quantity reduced: " + b);


            // remove from order item
            // System.out.println("Before orderitempojo quantity reduced: " + p);
            p.setOrderedQuantity(p.getOrderedQuantity() - reduceQuantity);
            dao.update(p);
            // System.out.println("After orderitempojo quantity reduced: " + p);

            // allocate in inventory
            InventoryPojo inventoryPojo = inventoryService.getByGlobalSkuId(p.getGlobalSkuId());
            // System.out.println("Before inventory quantity allocation: " + inventoryPojo);
            inventoryPojo
                    .setAllocatedQuantity(inventoryPojo.getAllocatedQuantity() + reduceQuantity);
            inventoryService.update(inventoryPojo.getId(), inventoryPojo);
            // System.out.println("After inventory quantity allocation: " + inventoryPojo);

            // allocate in order item p
            // System.out.println("Before orderitempojo quantity allocation: " + p);
            p.setAllocatedQuantity(p.getAllocatedQuantity() + reduceQuantity);
            dao.update(p);
            // System.out.println("After orderitempojo quantity allocation: " + p);
        }
    }

    @Transactional(rollbackFor = ApiException.class)
    public void generateInvoice(Long id) throws ApiException {
        List<OrderItemPojo> orderItemPojos = getByOrderId(id);
        // System.out.println("Will generate following: " + orderItemPojos);
        if (orderItemPojos.size() == 0)
            throw new ApiException("Can not generate empty order!!");
        for (OrderItemPojo p : orderItemPojos) {
            generateInvoice(p);
        }
    }

    @Transactional(rollbackFor = ApiException.class)
    private void generateInvoice(OrderItemPojo p) throws ApiException {
        Long reduceQuantity = p.getAllocatedQuantity();

        InventoryPojo inventoryPojo = inventoryService.getByGlobalSkuId(p.getGlobalSkuId());
        // reduce allocation from inventory
        // System.out.println("Before inventory quantity fulfillment reduce: " + inventoryPojo);
        inventoryPojo.setAllocatedQuantity(inventoryPojo.getAllocatedQuantity() - reduceQuantity);
        inventoryService.update(inventoryPojo.getId(), inventoryPojo);
        // System.out.println("After inventory quantity fulfillment reduce: " + inventoryPojo);

        // reduce allocation in order line item
        // System.out.println("Before orderitempojo quantity fulfillment reduce: " + p);
        p.setAllocatedQuantity(p.getAllocatedQuantity() - reduceQuantity);
        dao.update(p);
        // System.out.println("After orderitempojo quantity fulfillment reduce: " + p);

        // increase fulfillment in inventory
        // System.out.println("Before inventory quantity fulfillment: " + inventoryPojo);
        inventoryPojo.setFulfilledQuantity(inventoryPojo.getFulfilledQuantity() + reduceQuantity);
        inventoryService.update(inventoryPojo.getId(), inventoryPojo);
        // System.out.println("After inventory quantity fulfillment: " + inventoryPojo);

        // increase fulfillment in order line item
        // System.out.println("Before orderitempojo quantity fulfillment: " + p);
        p.setFulfilledQuantity(p.getFulfilledQuantity() + reduceQuantity);
        dao.update(p);
        // System.out.println("After orderitempojo quantity fulfillment: " + p);

    }
}
