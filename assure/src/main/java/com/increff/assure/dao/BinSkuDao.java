package com.increff.assure.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import com.increff.assure.pojo.BinSkuPojo;
import org.springframework.stereotype.Repository;

@Repository
public class BinSkuDao extends AbstractDao {

    private static final String SELECT_ID = "SELECT P FROM assure_bin_sku P WHERE ID=:id";
    private static final String SELECT_GLOBALSKUID =
            "SELECT P FROM assure_bin_sku P WHERE GLOBALSKUID=:globalSkuId";
    private static final String SELECT_GLOBALSKUID_BINID =
            "SELECT P FROM assure_bin_sku P WHERE GLOBALSKUID=:globalSkuId AND BINID=:binId";
    private static final String SELECT_ALL = "SELECT P FROM assure_bin_sku P";
    private static final String SELECT_BINID = "SELECT P FROM assure_bin_sku P WHERE BINID=:binId";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(BinSkuPojo p) {
        em.persist(p);
    }

    public BinSkuPojo select(Long id) {
        TypedQuery<BinSkuPojo> query = getQuery(SELECT_ID, BinSkuPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<BinSkuPojo> selectByGlobalSkuId(Long globalSkuId) {
        TypedQuery<BinSkuPojo> query = getQuery(SELECT_GLOBALSKUID, BinSkuPojo.class);
        query.setParameter("globalSkuId", globalSkuId);
        return query.getResultList();
    }

    public List<BinSkuPojo> selectAll() {
        TypedQuery<BinSkuPojo> query = getQuery(SELECT_ALL, BinSkuPojo.class);
        return query.getResultList();
    }

    public List<BinSkuPojo> selectByBinId(Long binId) {
        TypedQuery<BinSkuPojo> query = getQuery(SELECT_BINID, BinSkuPojo.class);
        query.setParameter("binId", binId);
        return query.getResultList();
    }

    public void update(BinSkuPojo p) {}

    public BinSkuPojo selectByGlobalSkuIdBinId(Long globalSkuId, Long binId) {
        TypedQuery<BinSkuPojo> query = getQuery(SELECT_GLOBALSKUID_BINID, BinSkuPojo.class);
        query.setParameter("globalSkuId", globalSkuId);
        query.setParameter("binId", binId);
        return getSingle(query);
    }


}
