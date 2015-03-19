<%-- 
    Document   : get_request_header
    Created on : 14-okt-2013, 14:22:17
    Author     : Peter
--%>

<html>
    <head>
        <title>Test Brouwsers</title>
    </head>
    <body>

        <h1>Request Headers</h1>

        <table cellpadding="5" border="1">

            <tr align="left">
                <th>Name</th>
                <th>Value</th>
            </tr>
            <%@ page import="java.util.Enumeration" %>
            <%
                Enumeration headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String name = (String) headerNames.nextElement();
                    String value = request.getHeader(name);
            %>
            <tr>
                <td width="120"><%= name%></td>
                <td><%= value%></td>
            </tr>
            <% }%>

        </table>
            <br>
        <table cellpadding="5" border="1">
            <tr align="left">
                <th>Cookie Name</th>
                <th>Cookie value</th>
            </tr>
            <%Cookie[] cookies = request.getCookies();
                for (Cookie c : cookies) {
            %>
            <tr>
                <td align="right"><%= c.getName()%></td>
                <td><%= c.getValue()%></td>
            </tr>
            <%
                }
            %>
        </table>


    </body>
</html>
