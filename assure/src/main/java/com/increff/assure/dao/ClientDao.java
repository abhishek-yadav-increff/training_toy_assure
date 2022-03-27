package com.increff.assure.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import com.increff.assure.pojo.ClientPojo;
import com.increff.commons.enums.UserEnum;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao extends AbstractDao {

    private static final String SELECT_ID = "SELECT P FROM assure_client P WHERE ID=:id";
    private static final String SELECT_NAME_USERTYPE =
            "SELECT P FROM assure_client P WHERE NAME=:name AND USERTYPE=:userType";
    private static final String SELECT_ALL = "SELECT P FROM assure_client P";
    private static final String QUERY_NAME = "SELECT P FROM assure_client P WHERE NAME LIKE :name";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ClientPojo p) {
        em.persist(p);
    }

    public ClientPojo select(Long id) {
        TypedQuery<ClientPojo> query = getQuery(SELECT_ID, ClientPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<ClientPojo> selectAll() {
        TypedQuery<ClientPojo> query = getQuery(SELECT_ALL, ClientPojo.class);
        return query.getResultList();
    }

    public List<ClientPojo> queryByName(String name) {
        TypedQuery<ClientPojo> query = getQuery(QUERY_NAME, ClientPojo.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    public Object getByNameAndType(String name, UserEnum userType) {
        TypedQuery<ClientPojo> query = getQuery(SELECT_NAME_USERTYPE, ClientPojo.class);
        query.setParameter("name", name);
        query.setParameter("userType", UserEnum.valueOf(userType.toString()).ordinal());

        return getSingle(query);
    }



}
