package com.increff.channel.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import com.increff.channel.pojo.ChannelPojo;
import org.springframework.stereotype.Repository;

@Repository
public class ChannelDao extends AbstractDao {

    private static final String SELECT_ID = "SELECT P FROM assure_channel P WHERE ID=:id";
    private static final String SELECT_NAME = "SELECT P FROM assure_channel P WHERE NAME=:name";
    private static final String SELECT_ALL = "SELECT P FROM assure_channel P";
    private static final String QUERY_NAME = "SELECT P FROM assure_channel P WHERE NAME LIKE :name";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ChannelPojo p) {
        em.persist(p);
    }

    public ChannelPojo select(Long id) {
        TypedQuery<ChannelPojo> query = getQuery(SELECT_ID, ChannelPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<ChannelPojo> selectAll() {
        TypedQuery<ChannelPojo> query = getQuery(SELECT_ALL, ChannelPojo.class);
        return query.getResultList();
    }

    public void update(ChannelPojo p) {}

    public List<ChannelPojo> queryByName(String name) {
        TypedQuery<ChannelPojo> query = getQuery(QUERY_NAME, ChannelPojo.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    public ChannelPojo selectByName(String name) {
        TypedQuery<ChannelPojo> query = getQuery(SELECT_NAME, ChannelPojo.class);
        query.setParameter("name", name);
        return getSingle(query);
    }



}
