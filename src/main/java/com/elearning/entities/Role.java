package com.elearning.entities;


public enum Role {

	ROLE_MEMBER(1), ROLE_ADMIN(2);
	
	private int value;
	
	Role(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
    public static Role findByAbbr(int item) {
        for (Role object : values()) {
            if (object.value == item) {
                return object;
            }
        }
        return null;
    }
	
	
}
