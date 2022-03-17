package com.increff.assure.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import lombok.Getter;
import lombok.Setter;

/**
 * BinPojo
 */
@Entity(name = "assure_bin")
@Getter
@Setter
public class BinPojo {

    @Id
    @TableGenerator(name = "bin_id", table = "generator_table", pkColumnName = "bin_id",
            initialValue = 10000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "bin_id")
    private long binId;


}
