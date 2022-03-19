package com.increff.assure.service;

import java.util.List;
import com.increff.assure.dao.InventoryDao;
import com.increff.assure.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(InventoryPojo p) throws ApiException {
        dao.insert(p);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public InventoryPojo get(Long id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public InventoryPojo getByGlobalSkuId(Long globalSkuId) throws ApiException {
        InventoryPojo inventoryPojo = dao.selectByGlobalSkuId(globalSkuId);
        if (inventoryPojo == null) {
            throw new ApiException("Inventory does not exist, globalSkuId: " + globalSkuId);
        }
        return inventoryPojo;
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public List<InventoryPojo> getAll() throws ApiException {
        List<InventoryPojo> inventoryPojos = dao.selectAll();
        if (inventoryPojos == null) {
            throw new ApiException("No Inventory Category Pair in database!");
        }
        return inventoryPojos;
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public InventoryPojo getCheck(Long id) throws ApiException {
        InventoryPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Inventory with given ID does not exist, id: " + id);
        }
        return p;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(Long id, InventoryPojo p) throws ApiException {
        InventoryPojo ex = getCheck(id);
        dao.update(ex);
    }
}
