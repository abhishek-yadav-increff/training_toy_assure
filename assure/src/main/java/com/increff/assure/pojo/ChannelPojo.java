package com.increff.assure.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import com.increff.assure.enums.InvoiceEnum;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "assure_channel")
@Getter
@Setter
public class ChannelPojo extends AbstractPojo {

    @Id
    @TableGenerator(name = "channel_id", table = "generator_table", initialValue = 10000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "channel_id")
    private Long id;

    private String name;
    private InvoiceEnum invoiceType;

}
