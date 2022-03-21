package com.increff.common.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends Exception {

	private static final long serialVersionUID = 1L;

	private List<ErrorData> errorDatas;

	public ApiException(String string, List<ErrorData> errorDatas) {
		super(string);
		this.errorDatas = errorDatas;
	}

	public ApiException(String string) {
		super(string);
	}

}
