package com.increff.assure.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "assure_inventory")
@Getter
@Setter
public class InventoryPojo {

    @Id
    @TableGenerator(name = "inventory_id", table = "generator_table", initialValue = 10000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "inventory_id")
    private Long id;
    private Long globalSkuId;
    private Long availableQuantity;
    private Long allocatedQuantity;
    private Long fulfilledQuantity;

    public InventoryPojo(Long globalSkuId, Long availableQuantity) {
        this.globalSkuId = globalSkuId;
        this.availableQuantity = availableQuantity;
        this.allocatedQuantity = Long.valueOf(0);
        this.fulfilledQuantity = Long.valueOf(0);
    }

    public InventoryPojo() {
        this.allocatedQuantity = Long.valueOf(0);
        this.fulfilledQuantity = Long.valueOf(0);
    }

    @Override
    public String toString() {
        return "InventoryPojo [allocatedQuantity=" + allocatedQuantity + ", availableQuantity="
                + availableQuantity + ", fulfilledQuantity=" + fulfilledQuantity + ", globalSkuId="
                + globalSkuId + ", id=" + id + "]";
    }
}
