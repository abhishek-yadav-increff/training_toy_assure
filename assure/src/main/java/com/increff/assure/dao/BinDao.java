package com.increff.assure.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import com.increff.assure.pojo.BinPojo;
import org.springframework.stereotype.Repository;

@Repository
public class BinDao extends AbstractDao {

    private static final String SELECT_ID = "SELECT P FROM assure_bin P WHERE binId=:id";
    private static final String SELECT_ALL = "SELECT P FROM assure_bin P";
    private static final String SELECT_MAX = "SELECT MAX(binId) FROM assure_bin";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert() {
        BinPojo p = new BinPojo();
        em.persist(p);
    }

    public BinPojo select(Long binId) {
        TypedQuery<BinPojo> query = getQuery(SELECT_ID, BinPojo.class);
        query.setParameter("id", binId);
        return getSingle(query);
    }

    public List<BinPojo> selectAll() {
        TypedQuery<BinPojo> query = getQuery(SELECT_ALL, BinPojo.class);
        return query.getResultList();
    }

    public void update(BinPojo p) {}

    public Long selectMax() {
        TypedQuery<Long> query = getQuery(SELECT_MAX, Long.class);
        return getSingle(query);
    }

}
