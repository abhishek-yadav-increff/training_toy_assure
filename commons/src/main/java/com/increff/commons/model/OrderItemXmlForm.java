package com.increff.commons.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "orderitem")
public class OrderItemXmlForm {
    private Long globalSkuId;
    private Long fulfilledQuantity;
    private String sellingPricePerUnit;
    private String totalSellingPrice;

    @XmlElement
    public Long getGlobalSkuId() {
        return globalSkuId;
    }

    public void setGlobalSkuId(Long globalSkuId) {
        this.globalSkuId = globalSkuId;
    }

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

    @Override
    public String toString() {
        return "OrderItemXmlForm [fulfilledQuantity=" + fulfilledQuantity + ", globalSkuId="
                + globalSkuId + ", sellingPricePerUnit=" + sellingPricePerUnit
                + ", totalSellingPrice=" + totalSellingPrice + "]";
    }

}
