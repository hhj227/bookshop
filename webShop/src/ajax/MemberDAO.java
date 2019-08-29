package ajax;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import pro07.MemberVO;

public class MemberDAO {
	/*
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost/bookshop?serverTimezone=Asia/Seoul";
	private static final String user = "test";
	private static final String pwd = "test123";
	*/
	private Connection conn;
	private PreparedStatement ps;
	private DataSource dataFactory;
	
	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env"); //JNDI에 접근하기 위한 기본 경로 지정 
			dataFactory = (DataSource) envContext.lookup("db"); //톰캣 context.xml 파일에 설정한 name 값을 이용해 톰캣이 미리 연결해둔 dataSource를 받아온다 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public List listMembers(MemberVO memberVO) {
		List list = new ArrayList();
		String _name=memberVO.getName();
		try {
			//connDB();
			conn = dataFactory.getConnection(); //DataSource를 이용해 데이터베이스에 연결
			String query = "select * from t_member ";
			System.out.println("prepareStatememt: " + query);
			
			if((_name!=null && _name.length()!=0)){
				 query+=" where name=?";
				 ps = conn.prepareStatement(query);
				 ps.setString(1, _name);
			}else {
				ps = conn.prepareStatement(query);	
			}
			
			ResultSet rs = ps.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);
				list.add(vo);
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void addMember(MemberVO memberVO) {
		try {
			conn = dataFactory.getConnection();
			String id = memberVO.getId();
			String pwd = memberVO.getPwd();
			String name = memberVO.getName();
			String email = memberVO.getEmail();
			
			String query = "insert into t_member";
			query += "(id,pwd,name,email)";
			query += "values(?,?,?,?)";
			System.out.println("prepareStatememt: " + query);
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, pwd);
			ps.setString(3, name);
			ps.setString(4, email); //인자를 넣음
			ps.executeUpdate(); //query 실행 
			ps.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delMember(String id) {
		try {
			conn = dataFactory.getConnection();
			String query = "delete from t_member" + " where id=?";
			System.out.println("prepareStatement: "+query);
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
			ps.executeUpdate();
			ps.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isExisted(MemberVO memberVO) {
		boolean result = false;
		String id = memberVO.getId();
		String pwd = memberVO.getPwd();
		try {
			conn = dataFactory.getConnection();
			//id와 비밀번호가 테이블에 존재하면 true, 존재하지 않으면 false 조회
			String query = "select if(count(*),'true','false') as result from t_member " ;
					query += "where id=? And pwd=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, pwd);
			ResultSet rs = ps.executeQuery();
			rs.next();
			result = Boolean.parseBoolean(rs.getString("result"));
			System.out.println("result="+result);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}



	public boolean overlappedID(String id) {
		boolean result = false;
		try {
			conn = dataFactory.getConnection();
			String query = "select if(count(*), 'true', 'false') as result from t_member";
			query += " where id=?";
			System.out.println("prepareStatement: " + query);
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			result = Boolean.parseBoolean(rs.getString("result"));
			ps.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
/*
	private void connDB() {
		try {
			Class.forName(driver);
			System.out.println("Oracle 드라이버 로딩 성공");
			conn = DriverManager.getConnection(url, user, pwd);
			System.out.println("Connection 생성 성공");
			System.out.println("Statement 생성 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
}
