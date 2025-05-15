package com.rewardpoints.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CusTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String customerId;
	@Column(nullable = false)
	private LocalDate date;
	@Column(nullable = false)
	private int amount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	public CusTransaction(int id, String customerId, LocalDate date, int amount) {
		this.id = id;
		this.customerId = customerId;
		this.date = date;
		this.amount = amount;
	}
	
	public String toString() {
		return "CusTransaction [id=" + id + ", customerId=" + customerId + ", date=" + date + ", amount=" + amount
				+ "]";
	}
	public CusTransaction() {
	
	}
	
	

}
