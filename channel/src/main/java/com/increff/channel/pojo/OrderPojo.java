package com.increff.channel.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import com.increff.channel.enums.OrderEnum;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "channel_order")
@Getter
@Setter
public class OrderPojo {

    @Id
    @TableGenerator(name = "order_id", table = "generator_table", initialValue = 10000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "order_id")
    private Long id;
    private Long clientId;
    private Long customerId;
    private Long channelId;
    private String channelOrderId;
    private OrderEnum status;

    public OrderPojo() {
        this.status = OrderEnum.CREATED;
    }

}
