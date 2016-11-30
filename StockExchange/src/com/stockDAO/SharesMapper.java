package com.stockDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.stockBeans.Shares;

public class SharesMapper implements RowMapper<Shares>{

	@Override
	public Shares mapRow(ResultSet rs, int numRow) throws SQLException {
		
		Shares shares=new Shares();
		shares.setCmp_name(rs.getString("cmp_name"));
		shares.setShare_num(rs.getInt("shares"));
		
		return shares;
	}

}
