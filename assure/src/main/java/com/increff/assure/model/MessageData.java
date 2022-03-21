package com.increff.assure.model;

import java.util.List;
import com.increff.common.model.ErrorData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageData {

    private String message;
    private List<ErrorData> errorDatas;
}
