package com.increff.assure.service;

import java.util.List;
import com.increff.assure.dao.ProductDao;
import com.increff.assure.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(ProductPojo p) throws ApiException {
        dao.insert(p);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public ProductPojo get(Long id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public List<ProductPojo> getAll() throws ApiException {
        List<ProductPojo> productPojos = dao.selectAll();
        if (productPojos == null) {
            throw new ApiException("No Product Category Pair in database!");
        }
        return productPojos;
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public ProductPojo getCheck(Long id) throws ApiException {
        ProductPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Product with given GlobalSkuId does not exist, id: " + id);
        }
        return p;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(Long id, ProductPojo p) throws ApiException {
        ProductPojo ex = getCheck(id);
        ex.setName(p.getName());
        ex.setBrandId(p.getBrandId());
        ex.setMrp(p.getMrp());
        ex.setDescription(p.getDescription());
        dao.update(ex);
    }

}
