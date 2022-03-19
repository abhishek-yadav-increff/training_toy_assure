package com.increff.assure.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import com.increff.assure.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryDao extends AbstractDao {

    private static final String SELECT_ID = "SELECT P FROM assure_inventory P WHERE ID=:id";
    private static final String SELECT_GLOBALSKUID =
            "SELECT P FROM assure_inventory P WHERE GLOBALSKUID=:globalSkuId";
    private static final String SELECT_ALL = "SELECT P FROM assure_inventory P";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(InventoryPojo p) {
        em.persist(p);
    }

    public InventoryPojo select(Long id) {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ID, InventoryPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public InventoryPojo selectByGlobalSkuId(Long id) {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_GLOBALSKUID, InventoryPojo.class);
        query.setParameter("globalSkuId", id);
        return getSingle(query);
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL, InventoryPojo.class);
        return query.getResultList();
    }

    public void update(InventoryPojo p) {}

}
