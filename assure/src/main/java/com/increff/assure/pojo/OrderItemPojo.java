package com.increff.assure.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import com.increff.assure.dto.helper.CommonsHelper;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "assure_order_item")
@Getter
@Setter
public class OrderItemPojo {

    @Id
    @TableGenerator(name = "order_item_id", table = "generator_table", initialValue = 10000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "order_item_id")
    private Long id;
    private Long orderId;
    private Long globalSkuId;
    private Long orderedQuantity;
    private Long allocatedQuantity;
    private Long fulfilledQuantity;
    private Double sellingPricePerUnit;

    public OrderItemPojo() {
        this.allocatedQuantity = Long.valueOf(0);
        this.fulfilledQuantity = Long.valueOf(0);
    }

    public Double getTotalItemCost() {
        return CommonsHelper.normalize(this.fulfilledQuantity * this.sellingPricePerUnit);
    }

    @Override
    public String toString() {
        return "OrderItemPojo [allocatedQuantity=" + allocatedQuantity + ", fulfilledQuantity="
                + fulfilledQuantity + ", globalSkuId=" + globalSkuId + ", id=" + id + ", orderId="
                + orderId + ", orderedQuantity=" + orderedQuantity + ", sellingPricePerUnit="
                + sellingPricePerUnit + "]";
    }

}
