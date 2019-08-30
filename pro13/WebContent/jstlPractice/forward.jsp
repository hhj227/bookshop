<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="java.util.*, sec01.*" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
List membersList = new ArrayList();
MemberBean m1 = new MemberBean("lee", "1234", "이순신", "lee@test.com");
MemberBean m2 = new MemberBean("son", "1234", "손흥민", "son@test.com");
membersList.add(m1);
membersList.add(m2);
request.setAttribute("membersList", membersList);
%>    

<html>
<head>
   <meta charset=”UTF-8">
   <title>forward</title>
</head>
<body>
   <jsp:forward page="member2.jsp"></jsp:forward>
</body>
</html>