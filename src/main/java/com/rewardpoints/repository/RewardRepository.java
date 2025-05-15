package com.rewardpoints.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rewardpoints.entity.CusTransaction;
import java.util.List;


@Repository
public interface RewardRepository extends JpaRepository<CusTransaction, Integer> {
	
	List<CusTransaction> findByCustomerId(String custerId);

}
