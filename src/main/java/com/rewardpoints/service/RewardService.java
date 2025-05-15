package com.rewardpoints.service;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewardpoints.entity.CusTransaction;
import com.rewardpoints.entity.RewardResponse;
import com.rewardpoints.exception.InvalidAmountException;
import com.rewardpoints.repository.RewardRepository;

@Service
public class RewardService {

	@Autowired
	RewardRepository rewardRepository;

	public List<RewardResponse> calculateRewards() {

		List<CusTransaction> allTransactions = rewardRepository.findAll();

		Map<String, List<CusTransaction>> transactionsByCustomer = allTransactions.stream()
				.collect(Collectors.groupingBy(CusTransaction::getCustomerId));

		return transactionsByCustomer.entrySet().stream()
				.map(entry -> computeRewardForCustomer(entry.getKey(), entry.getValue())).collect(Collectors.toList());

	}

	public RewardResponse calculateRewardsForCustomer(String customerId) {
		List<CusTransaction> transactions = rewardRepository.findByCustomerId(customerId);
		if (transactions.isEmpty()) {
			throw new NoSuchElementException("No transactions found for customer: " + customerId);
		}
		return computeRewardForCustomer(customerId, transactions);
	}

	private RewardResponse computeRewardForCustomer(String customerId, List<CusTransaction> transactions) {
		Map<String, Integer> monthlyPoints = new HashMap<>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
		for (CusTransaction transaction : transactions) {
			if (transaction.getAmount() < 0) {
				throw new InvalidAmountException("Amount must be >= 0 for transaction ID: " + transaction.getId());
			}

//			String month = transaction.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase()
//					+ " " + transaction.getDate().getYear();

			int points = calculatePoints(transaction.getAmount());
			String month = transaction.getDate().format(formatter).toUpperCase();
			monthlyPoints.put(month, monthlyPoints.getOrDefault(month, 0) + points);
		}

		int totalPoints = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();
		return new RewardResponse(customerId, monthlyPoints, totalPoints);
	}

	private int calculatePoints(double amount) {
		 if (amount <= 50) return 0;
		    int points = 0;
		    if (amount > 100) {
		        points += (int)((amount - 100) * 2);
		        points += 50; // 1 point per dollar between 50 and 100
		    } else {
		        points += (int)(amount - 50); // 1 point per dollar between 50 and amount
		    }
		    return points;
	}

}
