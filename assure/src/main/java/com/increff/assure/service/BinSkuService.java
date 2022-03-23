package com.increff.assure.service;

import java.util.List;
import com.increff.assure.dao.BinSkuDao;
import com.increff.assure.pojo.BinSkuPojo;
import com.increff.assure.pojo.InventoryPojo;
import com.increff.commons.model.ApiException;
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
        Long rowCount = 1L;
        for (BinSkuPojo p : ps) {
            validateAdd(p, rowCount);
            BinSkuPojo binSkuPojo = getByGlobalSkuIdBinId(p.getGlobalSkuId(), p.getBinId());
            if (binSkuPojo != null) {
                InventoryPojo inventoryPojo = inventoryService.getByGlobalSkuId(p.getGlobalSkuId());
                inventoryPojo.setAvailableQuantity(inventoryPojo.getAvailableQuantity()
                        + p.getQuantity() - binSkuPojo.getQuantity());
                inventoryService.update(inventoryPojo.getId(), inventoryPojo);
                binSkuPojo.setQuantity(p.getQuantity());
                dao.update(p);
                System.out.println("Updating " + binSkuPojo + inventoryPojo);
            } else {
                InventoryPojo inventoryPojo =
                        new InventoryPojo(p.getGlobalSkuId(), p.getQuantity());
                inventoryService.add(inventoryPojo);
                dao.insert(p);
            }
        }
    }

    private void validateAdd(BinSkuPojo p, Long rowCount) throws ApiException {
        if (p.getGlobalSkuId() == null)
            throw new ApiException("Row: " + rowCount + ", Product doesn't exist!!");
        if (p.getQuantity() == null)
            throw new ApiException("Row: " + rowCount + ",Quantity can not be empty!!");
        if (p.getQuantity() < 0)
            throw new ApiException("Row: " + rowCount + ",Quantity can not be negative!!");
    }

    @Transactional(readOnly = true)
    public BinSkuPojo get(Long id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(readOnly = true)
    public BinSkuPojo getCheck(Long id) throws ApiException {
        BinSkuPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("BinSku with given ID does not exist, id: " + id);
        }
        return p;
    }

    @Transactional(readOnly = true)
    public List<BinSkuPojo> getAll() throws ApiException {
        List<BinSkuPojo> binSkuPojos = dao.selectAll();
        if (binSkuPojos == null) {
            throw new ApiException("No BinSku Category Pair in database!");
        }
        return binSkuPojos;
    }

    @Transactional(readOnly = true)
    public List<BinSkuPojo> getByGlobalSkuId(Long globalSkuId) throws ApiException {
        List<BinSkuPojo> binSkuPojo = dao.selectByGlobalSkuId(globalSkuId);
        if (binSkuPojo == null) {
            throw new ApiException("BinSku does not exist, globalSkuId: " + globalSkuId);
        }
        return binSkuPojo;
    }

    @Transactional(readOnly = true)
    public BinSkuPojo getByGlobalSkuIdBinId(Long globalSkuId, Long binId) throws ApiException {
        BinSkuPojo binSkuPojo = dao.selectByGlobalSkuIdBinId(globalSkuId, binId);
        return binSkuPojo;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(Long id, BinSkuPojo p) throws ApiException {
        BinSkuPojo ex = getCheck(id);

        InventoryPojo inventoryPojo = inventoryService.getByGlobalSkuId(ex.getGlobalSkuId());
        inventoryPojo.setAvailableQuantity(
                inventoryPojo.getAvailableQuantity() - ex.getQuantity() + p.getQuantity());
        inventoryService.update(inventoryPojo.getId(), inventoryPojo);

        ex.setQuantity(p.getQuantity());
        dao.update(ex);
    }
}
