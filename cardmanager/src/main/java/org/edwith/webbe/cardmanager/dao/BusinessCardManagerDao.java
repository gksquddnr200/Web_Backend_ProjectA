package org.edwith.webbe.cardmanager.dao;

import org.edwith.webbe.cardmanager.dto.BusinessCard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessCardManagerDao {
	
	private static String dburl = "jdbc:mysql://localhost:3306/card";
	private static String dbUser = "connectuser";
	private static String dbPassword = "connect123!@#";
	
    public List<BusinessCard> searchBusinessCard(String keyword){
    	List<BusinessCard> list = new ArrayList<>();
    	
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    	}catch(ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    	String sql = "SELECT * FROM card WHERE name like ?";
    	try(Connection conn =  DriverManager.getConnection(dburl,dbUser,dbPassword);
    			PreparedStatement ps = conn.prepareStatement(sql)){
    		ps.setString(1,"%"+keyword+"%");
    		try(ResultSet rs = ps.executeQuery()){
    			while(rs.next()) {
					String name = rs.getString(1);
					String phone = rs.getString(2);
					String companyName = rs.getString(3);
					BusinessCard businesscard = new BusinessCard(name,phone,companyName);
					list.add(businesscard);
					}
    			}catch(Exception e) {
					e.printStackTrace();
				}
    		}catch(Exception ex) {
				ex.printStackTrace();
			}
    	return list;
    	}
    	

    public void addBusinessCard(BusinessCard businessCard){
    	int insertCount = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			//mysql이 메모리에 올라오는 작업(드라이버 로딩)
			Class.forName("com.mysql.cj.jdbc.Driver");
			//db url, user, password 입력
			conn = DriverManager.getConnection(dburl,dbUser,dbPassword);
			//실행하려는 쿼리문
			String sql = "INSERT INTO card (name, phone,companyName) VALUES(?, ?, ?)";
			ps = conn.prepareStatement(sql);
			// 쿼리문에 있는 ?에 들어갈 값을 정해준다
			// setInt(a,b) a: 위에 들어갈?의 순서, b: a에 이 값을 넣어주세요
			ps.setString(1,businessCard.getName());
			ps.setString(2,businessCard.getPhone());
			ps.setString(3,businessCard.getCompanyName());
			insertCount = ps.executeUpdate();

			
		} catch (Exception e) {
			e.printStackTrace();
		//반드시 수행되는 구절
		}finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {}			
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		
	}	
    
}
