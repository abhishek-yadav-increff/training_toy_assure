package com.increff.commons.model;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * OrderXmlForm
 */
@XmlRootElement
public class OrderXmlForm {
    private Long Id;
    private String clientName;
    private String customerName;
    private String channelName;
    private String channelOrderId;
    private String total;
    private String date;
    private List<OrderItemXmlForm> items;

    public OrderXmlForm(OrderData p, String clientName, String customerName, String channelName) {
        this.clientName = clientName;
        this.customerName = customerName;
        this.channelName = channelName;
        this.channelOrderId = p.getChannelOrderId();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        this.date = simpleDateFormat.format(new java.util.Date());
    }

    public OrderXmlForm() {}

    @Override
    public String toString() {
        return "OrderXmlForm [channelName=" + channelName + ", channelOrderId=" + channelOrderId
                + ", clientName=" + clientName + ", customerName=" + customerName + ", date=" + date
                + ", items=" + items + ", total=" + total + "]";
    }

    @XmlElement
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @XmlElement
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @XmlElement
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
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
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement
    public List<OrderItemXmlForm> getItems() {
        return items;
    }

    public void setItems(List<OrderItemXmlForm> items) {
        this.items = items;
    }

    @XmlTransient
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }


}
