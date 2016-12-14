package com.stockDAO;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.stockBeans.Company;
import com.stockBeans.Customer;
import com.stockBeans.Shares;

public class StackJDBCTemplate implements StockDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private PlatformTransactionManager transactionManager;

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public Customer checkLogin(String userName, String password) {
		String SQL = "select * from customer where cust_uname = ? and password= ?";
		Customer customer;
		try {
			customer = jdbcTemplateObject
					.queryForObject(SQL, new Object[] { userName, password }, new CustomerMapper());

			return customer;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Customer> listCustomers() {

		return null;
	}

	public List<Shares> listShares(int cust_id) {
		String sql = "select cm.cmp_name, sh.shares from customer cc inner join shares sh on cc.cust_id=sh.cust_id "
				+ "inner join company cm on cm.cmp_id=sh.cmp_id where sh.cust_id=?";
		/*
		 * List<Map<String, Object>> listShares=null; try { listShares =
		 * jdbcTemplateObject.queryForList(sql,new Object[]{1},new
		 * SharesMapper()); System.out.println("Print result of select query");
		 * for (Map<String, Object> map : listShares) { for (Entry<String,
		 * Object> entry : map.entrySet()) {
		 * System.out.println(entry.getKey()+"     "
		 * +entry.getValue().toString()); } } return listShares; } catch
		 * (Exception e) {
		 * System.out.println("Exception has occured: "+e.getMessage()); return
		 * listShares; }
		 */

		List<Shares> list = null;
		try {
			list = jdbcTemplateObject.query(sql, new Object[] { cust_id }, new SharesMapper());
			for (Shares shares : list) {
				shares.toString();
			}

			return list;
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
			return list;
		}

	}

	public List<Company> listCompanies() {
		String sql = "select * from company";

		List<Company> list = null;
		try {
			list = jdbcTemplateObject.query(sql, new BeanPropertyRowMapper<Company>(Company.class));
			return list;

		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
			return list;
		}

	}

	public int getShareNumbers(String cust_id, String cmp_id) {

		String sql = "select shares from shares where cust_id=? and cmp_id=?;";
		int number = 0;

		try {
			number = jdbcTemplateObject.queryForInt(sql, new Object[] { cust_id, cmp_id });

		} catch (Exception e) {
			System.out.println("Error occured while getting number of shares: " + e.getMessage());
		}
		return number;
	}

	public Company getCompany(int cmpID) {

		String sql = "select * from company where cmp_id=?";
		Company company = null;
		try {
			company = (Company) jdbcTemplateObject.queryForObject(sql, new Object[] { cmpID },
					new BeanPropertyRowMapper<Company>(Company.class));
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
		}
		return company;
	}

	public boolean buyShres(int customerId, int cmp_id, int numOfSharestoBuy) {
		// String
		boolean success = false;
		String sql;
		TransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(definition);
		double shareValue = 0;
		try {
			sql = "select balance from customer where cust_id=?";
			double balance = jdbcTemplateObject.queryForObject(sql, new Object[] { customerId }, Double.class);
			sql = "select share_value from company where cmp_id=?";
			shareValue = jdbcTemplateObject.queryForObject(sql, new Object[] { cmp_id }, Double.class);
			double amountRequired = shareValue * numOfSharestoBuy;
			if (balance >= amountRequired) {
				sql = "update customer set balance = balance-? where cust_id=?";
				jdbcTemplateObject.update(sql, new Object[] { amountRequired, customerId });
				sql = "select shares from shares where cust_id=? and cmp_id=?";
				Integer shares = jdbcTemplateObject.queryForObject(sql, new Object[] { customerId, cmp_id },
						Integer.class);
				if (shares == null) {
					sql = "insert into shares values(?,?,?)";
					jdbcTemplateObject.update(sql, customerId, cmp_id, numOfSharestoBuy);
				} else {
					sql = "update shares set shares=shares+? where cust_id=? and cmp_id=?";
					jdbcTemplateObject.update(sql, numOfSharestoBuy, customerId, cmp_id);
				}
			}
			addTransactionDetails(customerId, cmp_id, shareValue, numOfSharestoBuy, "B", "Successful",
					"Successfully bought the shares");
			transactionManager.commit(status);
			success = true;
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
			transactionManager.rollback(status);
			addTransactionDetails(customerId, cmp_id, shareValue, numOfSharestoBuy, "B", "Unsuccessful",
					"Error occured: " + e.getMessage());
			return success;
		}

		System.out.println("updated the share successfully");
		return success;
	}

	public Customer getCustomer(int cust_id) {
		String sql = "select * from customer where cust_id=?";
		Customer customer = null;
		try {
			customer = (Customer) jdbcTemplateObject
					.queryForObject(sql, new Object[] { cust_id }, new CustomerMapper());
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
		}
		System.out.println(customer.toString());
		return customer;
	}

	public boolean sellShares(int customerId, int cmp_id, int numOfSharestoSell) {
		boolean success = false;

		String sql;
		TransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(definition);
		double share_value = 0;
		try {
			sql = "select share_value from company where cmp_id=?";
			share_value = jdbcTemplateObject.queryForObject(sql, new Object[] { cmp_id }, Double.class);
			double amountGain = numOfSharestoSell * share_value;

			sql = "update shares set shares=shares-? where cust_id=? and cmp_id=?";
			jdbcTemplateObject.update(sql, numOfSharestoSell, customerId, cmp_id);
			sql = "update customer set balance = balance+? where cust_id=?";
			jdbcTemplateObject.update(sql, new Object[] { amountGain, customerId });

			addTransactionDetails(customerId, cmp_id, share_value, numOfSharestoSell, "S", "Successful",
					"Successfully sold the shares");
			transactionManager.commit(status);
			success = true;
			System.out.println("Sold the share successfully");
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
			transactionManager.rollback(status);
			addTransactionDetails(customerId, cmp_id, share_value, numOfSharestoSell, "S", "Unsuccessful",
					"Error occured: " + e.getMessage());
			return success;
		}

		return success;
	}

	private void addTransactionDetails(int customerId, int cmp_id, double share_value, int numOfSharestoSell,
			String type, String status, String message) {
		String sql = "INSERT INTO transactions(cust_id,cmp_id,share_value_at_tran,number_of_shares,tran_type,tran_status,tran_message)"
				+ " VALUES(?,?,?,?,?,?,?)";
		jdbcTemplateObject.update(sql, customerId, cmp_id, share_value, numOfSharestoSell, type, status, message);
	}

	public List<Transactions> listTransactions(int cust_id) {
		String sql = "SELECT t.tran_id,t.cust_id,c.cmp_name,t.share_value_at_tran,t.number_of_shares,t.tran_type,t.tran_status,t.tran_message"
				 +" FROM transactions t INNER JOIN company c ON t.cmp_id=c.cmp_id WHERE t.cust_id=?";

		List<Transactions> transactions = null;
		try {
			transactions = jdbcTemplateObject.query(sql, new Object[] { cust_id }, new BeanPropertyRowMapper<Transactions>(Transactions.class));
			
			for (Transactions transactions2 : transactions) {
				System.out.println(transactions2.toString());
			}	
			return transactions;
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
			return transactions;
		}
	
	}
}
