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
public class ProductPojo extends AbstractPojo {

    @Id
    @TableGenerator(name = "product_id", table = "generator_table", initialValue = 10000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "product_id")
    private Long globalSkuId;

    private String clientSkuId;
    private Long clientId;
    private String name;
    private String brandId;
    private Double mrp;
    private String description;

    public ProductPojo() {}

    @Override
    public String toString() {
        return "ProductPojo [brandId=" + brandId + ", clientId=" + clientId + ", clientSkuId="
                + clientSkuId + ", description=" + description + ", globalSkuId=" + globalSkuId
                + ", mrp=" + mrp + ", name=" + name + "]";
    }

}
