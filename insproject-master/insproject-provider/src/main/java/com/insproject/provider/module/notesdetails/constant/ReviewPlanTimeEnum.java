package com.insproject.provider.module.notesdetails.constant;

public enum ReviewPlanTimeEnum {
	/**
	 * 5分钟
	 */
	FIVE_M(5 * 60),
	/**
	 * 30分钟
	 */
	THIRTY_M(30 * 60),
	/**
	 * 12小时
	 */
	TWELVE_H(12 * 60 * 60),
	/**
	 * 1天
	 */
	ONE_D(24 * 60 * 60),
	/**
	 * 两天
	 */
	TWO_D(2 * 24 * 60 * 60),
	/**
	 * 4天
	 */
	FOUR_D(4 * 24 * 60 * 60),
	/**
	 * 7天
	 */
	SEVEN_D(7 * 24 * 60 * 60),
	/**
	 * 15天
	 */
	FIFTEEN_D(15 * 24 * 60 * 60);

	private Integer value;

	ReviewPlanTimeEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
