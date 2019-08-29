package ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pro07.MemberVO;

@WebServlet("/member")
public class MemberServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		MemberDAO dao=new MemberDAO();
//		String command = request.getParameter("command");
		String id = (String)request.getParameter("id");
		System.out.println("id = "+id);
		boolean overlappedID = dao.overlappedID(id);
		
		if(overlappedID == true) {
			out.print("not_usable");
		}else {
			out.print("usable");
		}
		
//		if(command!=null&&command.contentEquals("addMember")) {
//			String _id = request.getParameter("id");
//			String _pwd = request.getParameter("pwd");
//			String _name = request.getParameter("name");
//			String _email = request.getParameter("email");
//			
//			MemberVO vo = new MemberVO();
//			vo.setId(_id);
//			vo.setPwd(_pwd);
//			vo.setName(_name);
//			vo.setEmail(_email);
//			dao.addMember(vo);
//			List membersList = dao.listMembers(vo);
//			request.setAttribute("membersList", membersList);
//		}else if(command!=null && command.contentEquals("delMember")) {
////			String _id = request.getParameter("id");
//			dao.delMember(id);
//		}

//		RequestDispatcher dispatch = request.getRequestDispatcher("viewMembers");
//		dispatch.forward(request, response); // 바인딩한 request를 viewMembers 서블릿으로 포워딩
}
}
