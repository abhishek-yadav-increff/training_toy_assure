package com.increff.assure.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "assure_bin_sku")
@Getter
@Setter
public class BinSkuPojo extends AbstractPojo {

    @Id
    @TableGenerator(name = "bin_sku_id", table = "generator_table", initialValue = 10000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "bin_sku_id")
    private Long id;
    private Long binId;
    private Long globalSkuId;
    private Long quantity;

    @Override
    public String toString() {
        return "BinSkuPojo [binId=" + binId + ", globalSkuId=" + globalSkuId + ", id=" + id
                + ", quantity=" + quantity + "]";
    }
}
