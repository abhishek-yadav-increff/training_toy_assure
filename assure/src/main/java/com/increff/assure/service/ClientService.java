package com.increff.assure.service;

import java.util.List;
import java.util.stream.Collectors;
import com.increff.assure.dao.ClientDao;
import com.increff.assure.dto.helper.CommonsHelper;
import com.increff.commons.enums.UserEnum;
import com.increff.assure.pojo.ClientPojo;
import com.increff.commons.model.ApiException;
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
        if (p.getName() == null)
            throw new ApiException("Name can not be empty!!");
        if (p.getUserType() == null)
            throw new ApiException("User Type was illegal!!");
        if (getByNameAndType(p) != null)
            throw new ApiException("User already exists!!");
        dao.insert(p);
    }

    private Object getByNameAndType(ClientPojo p) {
        return dao.getByNameAndType(p.getName(), p.getUserType());
    }

    @Transactional(readOnly = true)
    public ClientPojo get(Long id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(readOnly = true)
    public List<ClientPojo> getAll() throws ApiException {
        List<ClientPojo> clientPojos = dao.selectAll();
        if (clientPojos == null) {
            throw new ApiException("No Client Category Pair in database!");
        }
        return clientPojos;
    }

    @Transactional(readOnly = true)
    public ClientPojo getCheck(Long id) throws ApiException {
        ClientPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("User with given ID does not exist, id: " + id);
        }
        return p;
    }

    @Transactional(readOnly = true)
    private List<ClientPojo> queryByName(String name) throws ApiException {
        List<ClientPojo> clientPojos = dao.queryByName(name);
        if (clientPojos == null) {
            return null;
        }
        return clientPojos;
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<ClientPojo> getByQueryClient(String query) throws ApiException {
        List<ClientPojo> clientPojos = getByQuery(query);
        List<ClientPojo> clientPojos2 = clientPojos.stream()
                .filter(c -> c.getUserType().equals(UserEnum.CLIENT)).collect(Collectors.toList());
        return clientPojos2;
    }

    @Transactional(readOnly = true)
    public List<ClientPojo> getByQueryCustomer(String query) throws ApiException {
        List<ClientPojo> clientPojos = getByQuery(query);
        List<ClientPojo> clientPojos2 =
                clientPojos.stream().filter(c -> c.getUserType().equals(UserEnum.CUSTOMER))
                        .collect(Collectors.toList());
        return clientPojos2;
    }

}
