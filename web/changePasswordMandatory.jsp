<%-- 
    Document   : changePassword
    Created on : 11-jul-2013, 15:30:33
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
                src="javascripts/vhjavascript.js">
        </script>
        <h1>Vakantiehuisje</h1>
        <h2>Aanpassen van uw wachtwoord.</h2>
        <a href="passwordForgotten.jsp">Wachtwoord vergeten?</a>
        <br>

        <font color="red"><strong><i>${message}</i></strong></font>
    <c:if test="${message == null}">
        <BR>
    </c:if> 

    <form action="ChangePassword" method="post">
        <table cellspacing="5" border="0">
            <tr>
                <td align="right">Het oude wachtwoord:</td>
                <td><input type="password" name="oldPassword"></td>
            </tr>
            <tr>
                <td align="right">Het nieuwe wachtwoord:</td>
                <td><input type="password" name="newPassword"></td>
            </tr>
            <tr>
                <td align="right">Herhaal het nieuwe wachtwoord:</td>
                <td><input type="password" name="checkNewPassword"></td>
            </tr>
            <tr>
                <td></td>
                <td><br><input type="button" value="Aanpassen"
                               onclick="validateChangePassword(this.form)"></td>
            </tr>
        </table>

    </form>
    <p><small>
            &copy; Copyright 2013 Peter van Lierop
            All rights reserved
        </small></p>
</body>
</html>
