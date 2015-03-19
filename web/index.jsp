<%-- 
    Document   : index
    Created on : 9-sep-2013, 10:34:33
    Author     : Peter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="CSS/mystyle2.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vakantiehuisje</title>
    </head>
    <body>
        <script language="javascript" type="text/javascript"
                src="javascripts/myjavascript.js">
        </script>
        <h1>Vakantiehuisje</h1>
        <h3>Aanmeldscherm</h3>
        <a href="passwordForgotten.jsp">Wachtwoord vergeten?</a>
        <br>

        <font color="red"><strong><i>${message}</i></strong></font>
    <c:if test="${message == null}">
        <BR>
    </c:if> 

    <form action="CheckUserId" method="post">
        <table cellspacing="5" border="0">
            <tr>
                <td align="right">Inlognaam:</td>
                <td><input type="text" name="inlogName"</td>
            </tr>
            <tr>
                <td align="right">Wachtwoord:</td>
                <td><input type="password" name="passWord"</td>
            </tr>
            <tr>
                <td></td>
                <td><input type="button" value="Aanmelden"
                           onclick="validateInlog(this.form)"</td>
            </tr>
        </table>
    </form>

    <p><small>
            &copy; Copyright 2013 Peter van Lierop
            All rights reserved
        </small></p>
</body>
</html>