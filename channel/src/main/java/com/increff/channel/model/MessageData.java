package com.increff.channel.model;

import java.util.List;
import com.increff.commons.model.ErrorData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageData {

    private String message;
    private List<ErrorData> errorDatas;
}
