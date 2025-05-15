package com.rewardpoints.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rewardpoints.entity.CusTransaction;
import com.rewardpoints.entity.RewardResponse;
import com.rewardpoints.exception.InvalidAmountException;
import com.rewardpoints.repository.RewardRepository;
import com.rewardpoints.service.RewardService;

public class RewardServiceTest {

	@Mock
	private RewardRepository repositorRewardRepository;

	@InjectMocks
	private RewardService rewardService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCalculateRewardsForCustomer() {
		String customerId = "CUST001";

		List<CusTransaction> transactions = List.of(new CusTransaction(1, customerId, LocalDate.of(2025, 1, 15), 120),
				new CusTransaction(2, customerId, LocalDate.of(2025, 1, 20), 75));

		when(repositorRewardRepository.findByCustomerId(customerId)).thenReturn(transactions);

		RewardResponse response = rewardService.calculateRewardsForCustomer(customerId);
		assertEquals(customerId, response.getCustomerId());
		assertEquals(1, response.getMonthlyPoints().size());
		assertEquals(115, response.getTotalPoint());
		assertEquals(90 + 25, response.getMonthlyPoints().get("JANUARY 2025"));
	}

	@Test
	void testInvalidAmountThrowsException() {
		String customerId = "CUST002";
		List<CusTransaction> transactions = List.of(new CusTransaction(4, customerId, LocalDate.of(2025, 2, 10), -1));

		when(repositorRewardRepository.findByCustomerId(customerId)).thenReturn(transactions);

		assertThrows(InvalidAmountException.class, () -> rewardService.calculateRewardsForCustomer(customerId));
	}

}
