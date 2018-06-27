package com.insproject.provider.module.notesreviewplan.constant;

public enum ReviewStateEnum {
	/**
	 * 未复习
	 */
	NO(0),
	/**
	 * 已复习
	 */
	YES(1);

	private Integer value;

	ReviewStateEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
