package com.api.constants;

public enum ServiceLocation {

	SERVICE_LOCATION_A(1),
	SERVICE_LOCATION_B(1),
	SERVICE_LOCATION_C(1);
	
	int code;
	
	private ServiceLocation(int code) {
		this.code=code;
	}
	
	public int getCode() {
		return code;
	}
}
