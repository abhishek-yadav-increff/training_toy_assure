package com.increff.assure.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import com.increff.assure.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemDao extends AbstractDao {

    private static final String SELECT_ID =
            "SELECT P FROM assure_order_item P WHERE globalSkuId=:id";
    private static final String SELECT_ALL = "SELECT P FROM assure_order_item P";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo p) {
        em.persist(p);
    }

    public OrderItemPojo select(Long id) {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ID, OrderItemPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<OrderItemPojo> selectAll() {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL, OrderItemPojo.class);
        return query.getResultList();
    }

    public void update(OrderItemPojo p) {}

}
