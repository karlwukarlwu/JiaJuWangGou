<%--
  Created by IntelliJ IDEA.
  User: 23584
  Date: 2023/9/4
  Time: 12:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--直接请求转发需要的页面--%>
<%--这个类似我们的网址的入口页面--%>
<%--不要用page 用pageByName--%>
<jsp:forward page="/customerFurnServlet?action=pageByName&pageNo=1"></jsp:forward>