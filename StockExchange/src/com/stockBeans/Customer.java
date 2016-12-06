package com.stockBeans;

public class Customer {

	private int CustomerId;
	private String customerName;
	private String username;
	private double balance;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(int customerId) {
		CustomerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Customer [CustomerId=" + CustomerId + ", customerName=" + customerName + ", username=" + username
				+ ", balance=" + balance + "]";
	}
	
	
	

}
