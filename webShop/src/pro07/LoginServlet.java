package pro07;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ajax.MemberDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	ServletContext context = null;
	List user_list = new ArrayList();
	
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init 메소드 호출");
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroy 메소드 호출");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8");
		context = getServletContext();
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
	    String user_id=request.getParameter("user_id");
	    String user_pwd=request.getParameter("user_pw");
	    
	    MemberVO memberVO = new MemberVO();
	    memberVO.setId(user_id);
	    memberVO.setPwd(user_pwd);
	    MemberDAO dao = new MemberDAO();
	    boolean result = dao.isExisted(memberVO);
	    
	    if(result) {
	    	LoginImpl loginUser = new LoginImpl(user_id, user_pwd);
			if(session.isNew()) {
				session.setAttribute("loginUser", loginUser);
				user_list.add(user_id);
				context.setAttribute("user_list", user_list);
			}
	    	session.setAttribute("isLogon", true);
	    	session.setAttribute("login.id", user_id);
	    	session.setAttribute("login.pwd", user_pwd);
	    	out.print("<html><body>");
	    	out.println("<head>");
			out.println("<script  type='text/javascript'>");
			out.println("setTimeout('history.go(0);', 5000)");
			out.println("</script>");
			out.println("</head>");
			out.println("아이디는 " + loginUser.user_id + "<br>");
			out.println("총 접속자 수는 " + LoginImpl.total_user + "<br>");
			out.println("접속 아이디:<br>");
			List list = (ArrayList)context.getAttribute("user_list");
			for(int i=0;i<list.size();i++) {
				out.println(list.get(i)+"<br>");
			}
			out.println("<a href='logout?user_id="+user_id+"'>로그아웃 </a> <br>");
	    	out.print("안녕하세요 " + user_id + "님!!!<br>");
			out.print("<a href='show'>회원정보보기</a>");
			out.print("</body></html>");
	    } else {
	    	out.print("<html><body>회원 아이디가 틀립니다.");
			out.print("<a href='login.html'>  다시 로그인하기</a>");
			out.print("</body></html>");
	    }
	    
//	    String user_address = request.getParameter("user_address");
//	    String user_email = request.getParameter("user_email");
//	    String user_hp = request.getParameter("user_hp");
	    
//	    String data = "안녕하세요!<br> 로그인하셨습니다.<br><br>";
//		data += "<html><body>";
//		data += "아이디 : " + user_id;
//		data += "<br>";
//		data += "비밀번호 : " + user_pw;
//		data += "<br>";
//		data += "주소 : " + user_address;
//		data += "<br>";
//		data += "email : " + user_email;
//		data += "<br>";
//		data += "휴대 전화 : " + user_hp;
//		data += "</html></body>";
//		out.print(data);
//		
//		user_address = URLEncoder.encode(user_address,"utf-8");
//		out.print("<a href='/webShop/second?user_id=" + user_id 
//	             + "&user_pw=" + user_pw 
//	             + "&user_address=" + user_address
//	             + "'>두 번째 서블릿으로 보내기</a>");
//		data = "</body></html>";
//		out.print(data);
	}

}
