package com.rewardpoints.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewardpoints.entity.RewardResponse;
import com.rewardpoints.service.RewardService;

@RestController
@RequestMapping("/rewards")
public class RewardsController {

	private final RewardService rewardService;

	public RewardsController(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	@GetMapping("/calculate")
	public ResponseEntity<List<RewardResponse>> getRewards()

	{
		// System.out.println("Saif Ali Khan");
		List<RewardResponse> calculateRewards = rewardService.calculateRewards();
		return new ResponseEntity<>(calculateRewards, HttpStatus.OK);
	}

	@GetMapping("/calculate/{customerId}")
	public ResponseEntity<RewardResponse> getRewardsByCustomerId(@PathVariable String customerId) {
		RewardResponse response = rewardService.calculateRewardsForCustomer(customerId);
		return ResponseEntity.ok(response);
	}

}
