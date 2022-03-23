package com.increff.assure.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import com.increff.assure.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao extends AbstractDao {

    private static final String SELECT_ID = "SELECT P FROM assure_order P WHERE ID=:id";
    private static final String SELECT_ALL = "SELECT P FROM assure_order P";
    private static final String SELECT_CLIENTID_CUSTOMERID_CHANNELID_CHANNELORDERID =
            "SELECT P FROM assure_order P WHERE CLIENID=:clientId AND CUSTOMERID=:customerId AND CHANNELID=:channelId AND CHANNELORDERID=:channelOrderId ";
    private static final String SELECT_CHANNELORDERID =
            "SELECT P FROM assure_order P WHERE CHANNELORDERID=:channelOrderId ";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo p) {
        em.persist(p);
    }

    public OrderPojo select(Long id) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_ID, OrderPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery(SELECT_ALL, OrderPojo.class);
        return query.getResultList();
    }

    public OrderPojo selectByClientIdCustomerIdChannelIdChannelOrderId(Long clientId,
            Long customerId, Long channelId, String channelOrderId) {
        TypedQuery<OrderPojo> query =
                getQuery(SELECT_CLIENTID_CUSTOMERID_CHANNELID_CHANNELORDERID, OrderPojo.class);
        query.setParameter("clientId", clientId);
        query.setParameter("customerId", customerId);
        query.setParameter("channelId", channelId);
        query.setParameter("channelOrderId", channelOrderId);

        return getSingle(query);
    }

    public void update(OrderPojo p) {}

    public OrderPojo selectByChannelOrderId(String channelOrderId) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_CHANNELORDERID, OrderPojo.class);
        query.setParameter("channelOrderId", channelOrderId);
        return getSingle(query);
    }

}
