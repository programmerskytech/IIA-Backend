package com.astro.util;

import lombok.Data;

@Data
public class APIResponseStatus {

	private int statusCode;
	private String message;
	private Integer errorCode;
	private String errorType = null;
}
	