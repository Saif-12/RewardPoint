package com.rewardpoints.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.rewardpoints.entity.CusTransaction;
import com.rewardpoints.repository.RewardRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardControllerIntTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RewardRepository rewardRepository;

	@BeforeEach
	void setUp() {
		rewardRepository.deleteAll();

		// Insert test transactions
		rewardRepository.saveAll(List.of(new CusTransaction(1, "CUST001", LocalDate.of(2025, 1, 5), 120), 
				new CusTransaction(2, "CUST001", LocalDate.of(2025, 1, 10), 100), 
				new CusTransaction(3, "CUST001", LocalDate.of(2025, 2, 1), 45), 
				new CusTransaction(4, "CUST002", LocalDate.of(2025, 1, 20), 200), 
				new CusTransaction(5, "CUST002", LocalDate.of(2025, 3, 5), 80) 
		));
	}
	
	@Test
    void testGetRewardsForCustomer1() throws Exception {
        mockMvc.perform(get("/rewards/calculate/CUST001")
                        .accept(MediaType.APPLICATION_JSON))
        		.andDo(result -> {
                    System.out.println("Response: " + result.getResponse().getContentAsString());})
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("CUST001"))
                .andExpect(jsonPath("$.totalPoints").value(140)) // 90 + 10
                .andExpect(jsonPath("$.monthlyPoints['JANUARY 2025']").value(140));
              //  .andExpect(jsonPath("$.monthlyPoints['FEBRUARY 2025']").value(00));
    }
	
	@Test
    void testGetRewardsForCustomer2() throws Exception {
        mockMvc.perform(get("/rewards/calculate/CUST002")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("CUST002"))
                .andExpect(jsonPath("$.totalPoints").value(280)) // 250 + 30
                .andExpect(jsonPath("$.monthlyPoints['JANUARY 2025']").value(250))
                .andExpect(jsonPath("$.monthlyPoints['MARCH 2025']").value(30));
    }

}
