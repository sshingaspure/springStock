package com.stockDAO;

public class Transactions {
	private int tran_id;
	private int cust_id;
	private String cmp_name;
	private double share_value_at_tran;
	private int number_of_shares;
	private char tran_type;
	private String tran_status;
	private String tran_message;

	public int getTran_id() {
		return tran_id;
	}

	public void setTran_id(int tran_id) {
		this.tran_id = tran_id;
	}

	public int getCust_id() {
		return cust_id;
	}

	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}

	public String getCmp_name() {
		return cmp_name;
	}

	public void setCmp_name(String cmp_name) {
		this.cmp_name = cmp_name;
	}

	public double getShare_value_at_tran() {
		return share_value_at_tran;
	}

	public void setShare_value_at_tran(double share_value_at_tran) {
		this.share_value_at_tran = share_value_at_tran;
	}

	public int getNumber_of_shares() {
		return number_of_shares;
	}

	public void setNumber_of_shares(int number_of_shares) {
		this.number_of_shares = number_of_shares;
	}

	public char getTran_type() {
		return tran_type;
	}

	public void setTran_type(char tran_type) {
		this.tran_type = tran_type;
	}

	public String getTran_status() {
		return tran_status;
	}

	public void setTran_status(String tran_status) {
		this.tran_status = tran_status;
	}

	public String getTran_message() {
		return tran_message;
	}

	public void setTran_message(String tran_message) {
		this.tran_message = tran_message;
	}

	@Override
	public String toString() {
		return "Transactions [tran_id=" + tran_id + ", cust_id=" + cust_id + ", cmp_name=" + cmp_name
				+ ", share_value_at_tran=" + share_value_at_tran + ", number_of_shares=" + number_of_shares
				+ ", tran_type=" + tran_type + ", tran_status=" + tran_status + ", tran_message=" + tran_message + "]";
	}

	
}
