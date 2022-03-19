package com.increff.assure.service;

import java.util.List;
import com.increff.assure.dao.BinSkuDao;
import com.increff.assure.pojo.BinSkuPojo;
import com.increff.assure.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BinSkuService {

    @Autowired
    private BinSkuDao dao;

    @Autowired
    private InventoryService inventoryService;

    @Transactional(rollbackFor = ApiException.class)
    public void add(List<BinSkuPojo> ps) throws ApiException {
        System.out.println(ps);
        for (BinSkuPojo p : ps) {
            InventoryPojo inventoryPojo = new InventoryPojo(p.getGlobalSkuId(), p.getQuantity());
            inventoryService.add(inventoryPojo);
            dao.insert(p);
        }
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public BinSkuPojo get(Long id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public BinSkuPojo getByGlobalSkuId(Long globalSkuId) throws ApiException {
        BinSkuPojo binSkuPojo = dao.selectByGlobalSkuId(globalSkuId);
        if (binSkuPojo == null) {
            throw new ApiException("BinSku does not exist, globalSkuId: " + globalSkuId);
        }
        return binSkuPojo;
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public List<BinSkuPojo> getAll() throws ApiException {
        List<BinSkuPojo> binSkuPojos = dao.selectAll();
        if (binSkuPojos == null) {
            throw new ApiException("No BinSku Category Pair in database!");
        }
        return binSkuPojos;
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public BinSkuPojo getCheck(Long id) throws ApiException {
        BinSkuPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("BinSku with given ID does not exist, id: " + id);
        }
        return p;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(Long id, BinSkuPojo p) throws ApiException {
        BinSkuPojo ex = getCheck(id);
        InventoryPojo inventoryPojo = inventoryService.getByGlobalSkuId(ex.getGlobalSkuId());
        inventoryPojo.setAvailableQuantity(
                inventoryPojo.getAvailableQuantity() - ex.getQuantity() + p.getQuantity());
        inventoryService.update(inventoryPojo.getId(), inventoryPojo);
        dao.update(ex);
    }
}
