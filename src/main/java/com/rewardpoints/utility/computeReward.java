package com.rewardpoints.utility;

import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.rewardpoints.entity.CusTransaction;
import com.rewardpoints.entity.RewardResponse;
import com.rewardpoints.exception.InvalidAmountException;

public class computeReward {

	private RewardResponse computeRewardForCustomer(String customerId, List<CusTransaction> transactions) {
		Map<String, Integer> monthlyPoints = new HashMap<>();

		for (CusTransaction transaction : transactions) {
			if (transaction.getAmount() < 0) {
				throw new InvalidAmountException("Amount must be >= 0 for transaction ID: " + transaction.getId());
			}

			String month = transaction.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase()
					+ " " + transaction.getDate().getYear();

			int points = calculatePoints(transaction.getAmount());
			monthlyPoints.put(month, monthlyPoints.getOrDefault(month, 0) + points);
		}

		int totalPoints = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();
		return new RewardResponse(customerId, monthlyPoints, totalPoints);
	}

	private int calculatePoints(double amount) {
		int points = 0;
		if (amount > 100) {
			points += (int) ((amount - 100) * 2);
			amount = 100;
		}
		if (amount > 50) {
			points += (int) (amount - 50);
		}
		return points;
	}

}
