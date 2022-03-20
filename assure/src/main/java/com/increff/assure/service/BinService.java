package com.increff.assure.service;

import java.util.List;
import com.increff.assure.dao.BinDao;
import com.increff.assure.model.BinForm;
import com.increff.assure.pojo.BinPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BinService {

    @Autowired
    private BinDao dao;

    @Transactional(rollbackFor = ApiException.class)
    public Long add(BinForm p) throws ApiException {
        if (p.getBinSize() == null)
            throw new ApiException("Enter valid bin amount!!");
        if (p.getBinSize() <= 0)
            throw new ApiException("Can not add non-positive amount of bins!!");
        Long nextBin = dao.selectMax();
        if (nextBin == null)
            nextBin = Long.valueOf(0);
        for (int i = 1; i <= p.getBinSize(); i++) {
            dao.insert();
        }
        return nextBin;
    }

    // @Transactional(readOnly = true)
    // public BinPojo get(Long id) throws ApiException {
    // return getCheck(id);
    // }

    // @Transactional(readOnly = true)
    // public List<BinPojo> getAll() throws ApiException {
    // List<BinPojo> binPojos = dao.selectAll();
    // if (binPojos == null) {
    // throw new ApiException("No Bin Category Pair in database!");
    // }
    // return binPojos;
    // }

    // @Transactional(readOnly = true)
    // public BinPojo getCheck(Long id) throws ApiException {
    // BinPojo p = dao.select(id);
    // if (p == null) {
    // throw new ApiException("Bin with given ID does not exist, id: " + id);
    // }
    // return p;
    // }

}
