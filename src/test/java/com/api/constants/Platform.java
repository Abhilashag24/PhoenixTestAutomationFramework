package com.api.constants;

public enum Platform {

	FST(1),
	FRONTDESK(2);
	

	int code;
	
	private Platform(int code) {
		this.code=code;
	}
	
	public int getCode() {
		return code;
	}
	
	
	
	
}
