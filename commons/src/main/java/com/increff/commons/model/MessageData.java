package com.increff.commons.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageData {

    private String message;
    private List<ErrorData> errorDatas;
}
