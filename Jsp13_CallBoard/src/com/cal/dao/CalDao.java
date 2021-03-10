package com.cal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.cal.db.JDBCTemplete.*;

import com.cal.dto.CalDto;

public class CalDao {
	
	public int insertCalBoard(CalDto dto) {
		Connection con = getConnection();
		
		String sql = " INSERT INTO CALBOARD " + 
					" VALUES(CALBOARDSEQ.NEXTVAL, ?, ?, ?, ?, SYSDATE) ";
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, dto.getId());
			pstm.setString(2, dto.getTitle());
			pstm.setString(3, dto.getContent());
			pstm.setString(4, dto.getMdate());
			System.out.println("3. 쿼리 준비 " + sql);
			res = pstm.executeUpdate();
			System.out.println("4. 쿼리 실행 ");
			if (res > 0) {
				commit(con);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			close(pstm, con);
			System.out.println("db 종료");
		}
		
		return res;
		
	}
	
	public List<CalDto> getCalList(String id, String yyyyMMdd){
		
		Connection con = getConnection();
		
		String sql = " SELECT SEQ, ID, TITLE, CONTENT, MDATE, REGDATE "
					+" FROM CALBOARD "
					+" WHERE ID = ? "
					+" AND SUBSTR(MDATE,1,8) = ? ";
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		List<CalDto> list = new ArrayList<CalDto>();
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setString(2, yyyyMMdd);
			System.out.println("쿼리 준비 " + sql);
			rs = pstm.executeQuery();
			System.out.println("4. 쿼리 실행 ");
			while(rs.next()) {
				CalDto dto = new CalDto(
						rs.getInt(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5), 
						rs.getDate(6));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs, pstm, con);
			System.out.println("db 종료");
		}
		
		return list;
	}
	
	public List<CalDto> getCalViewList(String id, String yyyyMM){
		
		Connection con = getConnection();
		
		String sql = " SELECT * "
				+ "FROM "
				+ "	( "
				+ "	SELECT (ROW_NUMBER() OVER(PARTITION BY SUBSTR(MDATE, 1, 8) ORDER BY MDATE))RN, SEQ, ID, TITLE, CONTENT, MDATE, REGDATE "
				+ "	FROM CALBOARD "
				+ "	WHERE ID = ? "
				+ "	AND SUBSTR(MDATE,1,6) = ? "
				+ "	) "
				+ " WHERE RN BETWEEN 1 AND 3 ";
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		List<CalDto> list = new ArrayList<CalDto>();
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setString(2, yyyyMM);
			
			System.out.println("3. query 준비");
			
			rs = pstm.executeQuery();
			System.out.println("4. query 실행 및 리턴");
			
			
			while(rs.next()) {
				CalDto dto = new CalDto(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs, pstm, con);
			System.out.println("5. db 종료");
		}
		
		return list;
	}
}