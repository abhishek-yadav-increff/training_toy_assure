package com.increff.channel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelData extends ChannelForm {

    private Long id;

    @Override
    public String toString() {
        return "ChannelData [id=" + id + "]";
    }
}
