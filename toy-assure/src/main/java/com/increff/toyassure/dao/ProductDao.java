package com.increff.toyassure.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import com.increff.toyassure.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao extends AbstractDao {

    private static final String SELECT_ID = "SELECT P FROM assure_product P WHERE globalSkuId=:id";
    private static final String SELECT_ALL = "SELECT P FROM assure_product P";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ProductPojo p) {
        em.persist(p);
    }

    public ProductPojo select(Long id) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ID, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ALL, ProductPojo.class);
        return query.getResultList();
    }

    public void update(ProductPojo p) {}



}
