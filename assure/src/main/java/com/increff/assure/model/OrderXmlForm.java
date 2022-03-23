package com.increff.assure.model;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.increff.assure.pojo.OrderPojo;

/**
 * OrderXmlForm
 */
@XmlRootElement
public class OrderXmlForm {

    private Long id;
    private Long clientId;
    private Long customerId;
    private Long channelId;
    private String channelOrderId;
    private String total;
    private String date;
    private List<OrderItemXmlForm> items;

    public OrderXmlForm(OrderPojo p) {
        this.id = p.getId();
        this.clientId = p.getClientId();
        this.customerId = p.getCustomerId();
        this.channelId = p.getChannelId();
        this.channelOrderId = p.getChannelOrderId();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        this.date = simpleDateFormat.format(new java.util.Date());
    }

    public OrderXmlForm() {}

    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @XmlElement
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @XmlElement
    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    @XmlElement
    public String getChannelOrderId() {
        return channelOrderId;
    }

    public void setChannelOrderId(String channelOrderId) {
        this.channelOrderId = channelOrderId;
    }

    @XmlElement
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @XmlElement
    public List<OrderItemXmlForm> getItems() {
        return items;
    }

    public void setItems(List<OrderItemXmlForm> items) {
        this.items = items;
    }

    @XmlElement
    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

}
