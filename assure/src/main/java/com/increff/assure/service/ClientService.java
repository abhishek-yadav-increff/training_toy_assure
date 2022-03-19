package com.increff.assure.service;

import java.util.List;
import java.util.stream.Collectors;
import com.increff.assure.dao.ClientDao;
import com.increff.assure.dto.helper.CommonsHelper;
import com.increff.assure.enums.UserEnum;
import com.increff.assure.pojo.ClientPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {
    private static final Logger LOGGER = Logger.getLogger(ClientService.class);

    @Autowired
    private ClientDao dao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(ClientPojo p) throws ApiException {
        dao.insert(p);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public ClientPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public List<ClientPojo> getAll() throws ApiException {
        List<ClientPojo> clientPojos = dao.selectAll();
        if (clientPojos == null) {
            throw new ApiException("No Client Category Pair in database!");
        }
        return clientPojos;
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    public ClientPojo getCheck(int id) throws ApiException {
        ClientPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Client with given ID does not exist, id: " + id);
        }
        return p;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, ClientPojo p) throws ApiException {
        ClientPojo ex = getCheck(id);
        ex.setName(p.getName());
        ex.setUserType(p.getUserType());
        dao.update(ex);
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    private List<ClientPojo> queryByName(String name) throws ApiException {
        List<ClientPojo> clientPojos = dao.queryByName(name);
        if (clientPojos == null) {
            return null;
        }
        return clientPojos;
    }

    @Transactional(readOnly = true, rollbackFor = ApiException.class)
    private List<ClientPojo> queryById(Long id) throws ApiException {
        List<ClientPojo> clientPojos = getAll();
        if (clientPojos == null) {
            throw new ApiException("Clients don't exist!!");
        }
        List<ClientPojo> clientPojos2 =
                clientPojos.stream().filter(c -> c.getId().toString().matches(".*" + id + ".*"))
                        .collect(Collectors.toList());
        return clientPojos2;
    }

    public List<ClientPojo> getByQuery(String query) throws ApiException {
        LOGGER.info("Inside getByQuery");
        LOGGER.info("Query: " + query);
        if (query == null || query.isEmpty()) {
            LOGGER.info("Returning all");
            return dao.selectAll();
        } else if (CommonsHelper.isNumeric(query)) {
            LOGGER.info("Returning matched by id");
            return queryById(Long.parseLong(query));
        } else {
            LOGGER.info("Returning matched by name");
            List<ClientPojo> clientPojos = queryByName(query);
            LOGGER.info("Size of return: " + clientPojos.size());
            return clientPojos;
        }

    }

    public List<ClientPojo> getByQueryClient(String query) throws ApiException {
        List<ClientPojo> clientPojos = getByQuery(query);
        List<ClientPojo> clientPojos2 = clientPojos.stream()
                .filter(c -> c.getUserType().equals(UserEnum.CLIENT)).collect(Collectors.toList());
        return clientPojos2;
    }


    public List<ClientPojo> getByQueryCustomer(String query) throws ApiException {
        List<ClientPojo> clientPojos = getByQuery(query);
        List<ClientPojo> clientPojos2 =
                clientPojos.stream().filter(c -> c.getUserType().equals(UserEnum.CUSTOMER))
                        .collect(Collectors.toList());
        return clientPojos2;
    }

}
