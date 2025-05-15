package com.rewardpoints.entity;

import java.util.Map;

public class RewardResponse {

	private String customerId;
	private Map<String, Integer> monthlyPoints;
	private int totalPoint;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Map<String, Integer> getMonthlyPoints() {
		return monthlyPoints;
	}

	public void setMonthlyPoints(Map<String, Integer> monthlyPoints) {
		this.monthlyPoints = monthlyPoints;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	public RewardResponse(String customerId, Map<String, Integer> monthlyPoints, int totalPoint) {
		this.customerId = customerId;
		this.monthlyPoints = monthlyPoints;
		this.totalPoint = totalPoint;
	}

	public String toString() {
		return "RewardResponse [customerId=" + customerId + ", monthlyPoints=" + monthlyPoints + ", totalPoint="
				+ totalPoint + "]";
	}

}
