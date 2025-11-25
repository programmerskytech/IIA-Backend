package com.astro.util;

import lombok.Data;

@Data
public class APIResponse {
	private APIResponseStatus responseStatus;
	private Object responseData;
}