package CookieAndSession;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SessionTest")
public class SessionTest extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(); //기존 세션 반환 또는 객체 새로 생성
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		session.setAttribute("name", "이순신");
		out.println("<h1>세션에 이름을 바인딩합니다.</h1>");
		out.println("<a href='/webShop/test02/session1.jsp'>첫번째 페이지로 이동하기 </a><br>");
		if (session.isNew()) {
			if(user_id!= null) {
				session.setAttribute("user_id", user_id);
				String url = response.encodeURL("login");
				out.println("<a href="+url+">로그인 상태 확인</a>");
//				out.println("<a href='SessionTest'>로그인 상태 확인</a>");
			}
			else {
				out.print("<a href='login.html'>다시 로그인하세요</a>");
				session.invalidate();
			}
		} else {
			user_id = (String) session.getAttribute("user_id");
			if (user_id!=null&&user_id.length()!=0) {
				out.print("안녕하세요 "+user_id+"님~");
			}else {
				out.print("<a href='login.html'>다시 로그인하세요</a>");
				session.invalidate();
			}
		}
	}
	
}
