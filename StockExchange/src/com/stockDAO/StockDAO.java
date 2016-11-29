package com.stockDAO;

import java.util.List;

import javax.sql.DataSource;

import com.stockBeans.Customer;

public interface StockDAO {

	public void setDataSource(DataSource ds);
	
	public Customer checkLogin(String userName,String password);
	
	 public List<Customer> listCustomers();
	
}
