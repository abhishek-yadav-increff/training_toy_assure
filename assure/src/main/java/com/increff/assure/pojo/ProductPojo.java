package com.increff.assure.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "assure_product")
@Getter
@Setter
public class ProductPojo {

    @Id
    @TableGenerator(name = "product_id", table = "generator_table", pkColumnName = "product_id",
            initialValue = 10000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "product_id")
    private Long globalSkuId;

    private String clientSkuId;
    private Long clientId;
    private String name;
    private String brandId;
    private Double mrp;
    private String description;

    public ProductPojo() {}

}
