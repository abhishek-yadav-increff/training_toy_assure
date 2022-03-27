package com.increff.assure.dto;

import static org.junit.Assert.assertEquals;
import java.util.List;
import com.increff.commons.model.ClientData;
import com.increff.assure.service.AbstractUnitTest;
import com.increff.assure.utils.ClientUtil;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.ClientForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ClientDtoTest
 */
public class ClientDtoTest extends AbstractUnitTest {
    @Autowired
    private ClientDto clientDto;

    @Test
    public void testAddSuccess() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm(" naMe   ", " cliEnt ");
        clientDto.add(clientForm);

        List<ClientData> clientDatas = clientDto.getAll();
        assertEquals(1, clientDatas.size());

        ClientData clientData = clientDto.get(clientDatas.get(0).getId());
        assertEquals(clientData.getName(), "name");
        assertEquals(clientData.getUserEnum(), "CLIENT");
    }

    @Test(expected = ApiException.class)
    public void testAddNullName() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm(null, "  cliEnt  ");
        clientDto.add(clientForm);
    }

    @Test(expected = ApiException.class)
    public void testAddNullClientType() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", null);
        clientDto.add(clientForm);
    }

    @Test(expected = ApiException.class)
    public void testAddEmptyName() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("", "  cliEnt  ");
        clientDto.add(clientForm);
    }

    @Test(expected = ApiException.class)
    public void testAddEmptyClientType() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", "");
        clientDto.add(clientForm);
    }

    @Test(expected = ApiException.class)
    public void testAddInvalidClientType() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm("name", " channEl ");
        clientDto.add(clientForm);
    }

    @Test(expected = ApiException.class)
    public void testAddDuplicate() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm(" naMe ", " cliEnt ");
        clientDto.add(clientForm);
        clientDto.add(clientForm);
    }

    @Test
    public void testGetSuccess() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm(" naMe   ", " cliEnt ");
        clientDto.add(clientForm);

        List<ClientData> clientDatas = clientDto.getAll();
        assertEquals(1, clientDatas.size());

        ClientData clientData = clientDto.get(clientDatas.get(0).getId());
        assertEquals(clientData.getName(), "name");
        assertEquals(clientData.getUserEnum(), "CLIENT");
    }

    @Test
    public void testGetAllSuccess() throws ApiException {
        ClientForm clientForm = ClientUtil.getClientForm(" naMe   ", " cliEnt ");
        clientDto.add(clientForm);
        clientForm = ClientUtil.getClientForm(" naMeE   ", " cliEnt ");
        clientDto.add(clientForm);

        List<ClientData> clientDatas = clientDto.getAll();
        assertEquals(2, clientDatas.size());
    }

}
