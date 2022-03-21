package com.increff.assure.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import com.increff.assure.pojo.ChannelListingPojo;
import org.springframework.stereotype.Repository;

@Repository
public class ChannelListingDao extends AbstractDao {

    private static final String SELECT_ID = "SELECT P FROM assure_channellisting P WHERE ID=:id";
    private static final String SELECT_ALL = "SELECT P FROM assure_channellisting P";
    private static final String SELECT_CLIENTID_CHANNELID_GLOBALSKUID =
            "SELECT P FROM assure_channellisting P WHERE CLIENTID=:clientId AND CHANNELID=:channelId AND GLOBALSKUID=:globalSkuId";
    private static final String SELECT_CLIENTID_CHANNELID_CHANNELSKUID =
            "SELECT P FROM assure_channellisting P WHERE CLIENTID=:clientId AND CHANNELID=:channelId AND CHANNELSKUID=:channelSkuId";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ChannelListingPojo p) {
        em.persist(p);
    }

    public ChannelListingPojo select(Long id) {
        TypedQuery<ChannelListingPojo> query = getQuery(SELECT_ID, ChannelListingPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<ChannelListingPojo> selectAll() {
        TypedQuery<ChannelListingPojo> query = getQuery(SELECT_ALL, ChannelListingPojo.class);
        return query.getResultList();
    }

    public void update(ChannelListingPojo p) {}

    public ChannelListingPojo getByClientIdChanneIdGlobalSkuId(Long clientId, Long channelId,
            Long globalSkuId) {
        TypedQuery<ChannelListingPojo> query =
                getQuery(SELECT_CLIENTID_CHANNELID_GLOBALSKUID, ChannelListingPojo.class);
        query.setParameter("clientId", clientId);
        query.setParameter("channelId", channelId);
        query.setParameter("globalSkuId", globalSkuId);
        return getSingle(query);
    }

    public ChannelListingPojo getByClientIdChanneIdGlobalSkuId(Long clientId, Long channelId,
            String channelSkuId) {
        TypedQuery<ChannelListingPojo> query =
                getQuery(SELECT_CLIENTID_CHANNELID_CHANNELSKUID, ChannelListingPojo.class);
        query.setParameter("clientId", clientId);
        query.setParameter("channelId", channelId);
        query.setParameter("channelSkuId", channelSkuId);
        return getSingle(query);
    }



}
