package com.stockDAO;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.stockBeans.Customer;

public class StackJDBCTemplate implements StockDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource=ds;
		this.jdbcTemplateObject=new JdbcTemplate(dataSource);
	}

	@Override
	public Customer checkLogin(String userName, String password) {
		 String SQL = "select * from customer where username = ? and password= ?";
	      Customer customer = jdbcTemplateObject.queryForObject(SQL, 
	                        new Object[]{userName,password}, new StudentMapper());
		
		return customer;
	}

	@Override
	public List<Customer> listCustomers() {
		
		return null;
	}

}
