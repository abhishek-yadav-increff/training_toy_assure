package com.increff.channel.pojo;

import java.time.ZonedDateTime;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;

/**
 * AbstractPojo
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractPojo {

    public ZonedDateTime created_at;
    public ZonedDateTime updated_at;

    @Version
    public Long version;

    @PrePersist
    protected void onCreate() {
        this.created_at = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = ZonedDateTime.now();
    }


}
