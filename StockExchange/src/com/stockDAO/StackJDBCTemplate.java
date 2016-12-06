package com.stockDAO;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.stockBeans.Company;
import com.stockBeans.Customer;
import com.stockBeans.Shares;

public class StackJDBCTemplate implements StockDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

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
			list = jdbcTemplateObject.query(sql, new BeanPropertyRowMapper(Company.class));

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
			System.out.println("Error occured: " + e.getMessage());
		}
		return number;
	}

}
