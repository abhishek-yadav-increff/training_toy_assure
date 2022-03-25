package com.increff.commons.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "orderitem")
public class OrderItemXmlForm {
    private String clientSkuId;
    private String channelSkuId;
    private String name;
    private Long fulfilledQuantity;
    private String sellingPricePerUnit;
    private String totalSellingPrice;


    @XmlElement
    public Long getFulfilledQuantity() {
        return fulfilledQuantity;
    }

    public void setFulfilledQuantity(Long fulfilledQuantity) {
        this.fulfilledQuantity = fulfilledQuantity;
    }

    @XmlElement
    public String getSellingPricePerUnit() {
        return sellingPricePerUnit;
    }

    public void setSellingPricePerUnit(String sellingPricePerUnit) {
        this.sellingPricePerUnit = sellingPricePerUnit;
    }

    @XmlElement
    public String getTotalSellingPrice() {
        return totalSellingPrice;
    }

    public void setTotalSellingPrice(String totalSellingPrice) {
        this.totalSellingPrice = totalSellingPrice;
    }


    @XmlElement
    public String getClientSkuId() {
        return clientSkuId;
    }

    public void setClientSkuId(String clientSkuId) {
        this.clientSkuId = clientSkuId;
    }

    @XmlElement
    public String getChannelSkuId() {
        return channelSkuId;
    }

    public void setChannelSkuId(String channelSkuId) {
        this.channelSkuId = channelSkuId;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OrderItemXmlForm [channelSkuId=" + channelSkuId + ", clientSkuId=" + clientSkuId
                + ", fulfilledQuantity=" + fulfilledQuantity + ", name=" + name
                + ", sellingPricePerUnit=" + sellingPricePerUnit + ", totalSellingPrice="
                + totalSellingPrice + "]";
    }

}
