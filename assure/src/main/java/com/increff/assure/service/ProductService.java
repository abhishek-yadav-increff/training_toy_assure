package com.increff.assure.service;

import java.util.List;
import com.increff.assure.dao.ProductDao;
import com.increff.assure.pojo.ProductPojo;
import com.increff.common.model.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    @Autowired
    private ClientService clientService;

    @Transactional(rollbackFor = ApiException.class)
    public void add(ProductPojo p) throws ApiException {
        validateAdd(p);
        if (getClientIdClientSkuId(p.getClientId(), p.getClientSkuId()) != null) {
            throw new ApiException(
                    "Product under same Client and Client Sku combination already exists!!");
        }
        if (clientService.get(p.getClientId()) == null)
            throw new ApiException("Client with given ID doesn't exists!!");
        dao.insert(p);
    }

    private void validateAdd(ProductPojo productPojo) throws ApiException {
        if (productPojo.getClientSkuId() == null || productPojo.getClientSkuId().isEmpty())
            throw new ApiException("Client Sku ID can not be empty!!");
        if (productPojo.getClientId() == null)
            throw new ApiException("Client ID can not be empty!!");
        validateUpdate(productPojo);
    }

    @Transactional(readOnly = true)
    public ProductPojo get(Long id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(readOnly = true)
    public ProductPojo getCheck(Long id) throws ApiException {
        ProductPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Product with given GlobalSkuId does not exist, id: " + id);
        }
        return p;
    }

    @Transactional(readOnly = true)
    public ProductPojo getClientIdClientSkuId(Long clientId, String clientSkuId)
            throws ApiException {
        ProductPojo productPojo = dao.selectClientIdClientSkuId(clientSkuId, clientId);
        return productPojo;
    }

    @Transactional(readOnly = true)
    public List<ProductPojo> getAll() throws ApiException {
        List<ProductPojo> productPojos = dao.selectAll();
        if (productPojos == null) {
            throw new ApiException("No Product Category Pair in database!");
        }
        return productPojos;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(Long id, ProductPojo p) throws ApiException {
        ProductPojo ex = getCheck(id);
        validateUpdate(p);
        ex.setName(p.getName());
        ex.setBrandId(p.getBrandId());
        ex.setMrp(p.getMrp());
        ex.setDescription(p.getDescription());
        dao.update(ex);
    }

    private void validateUpdate(ProductPojo productPojo) throws ApiException {
        if (productPojo.getName() == null || productPojo.getName().isEmpty())
            throw new ApiException("Name can not be empty!!");
        if (productPojo.getBrandId() == null || productPojo.getBrandId().isEmpty())
            throw new ApiException("Brand ID can not be empty!!");
        if (productPojo.getMrp() == null)
            throw new ApiException("MRP can not be empty!!");
        else if (productPojo.getMrp() <= 0)
            throw new ApiException("MRP must be positive!!");
        if (productPojo.getDescription() == null || productPojo.getDescription().isEmpty())
            throw new ApiException("Description can not be empty!!");
    }

}
