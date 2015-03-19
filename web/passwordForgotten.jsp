<%-- 
    Document   : passwordForgotten
    Created on : 25-feb-2013, 9:38:50
    Author     : Peter
--%>

<!DOCTYPE html>
<html>
    <head>
        <title>Vakantiehuisje</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>

    <body>
        <script language="javascript" type="text/javascript"
                src="includes/myjavascript.js">
        </script>
        <h1>Vakantiehuisje</h1>
        <h3>Wachtwoord vergeten</h3>
        <p><font color="red"><strong><i>${message}</i></strong></font></p>
        <form action="RequestNewPassword" method="post">
            <table cellspacing="5" border="0">
                <tr>
                    <td align="right">Inlognaam:</td>
                    <td><input type="text" name="inLogName"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><br><input type="button" value="Nieuw wachtwoord aanvragen"
                                   onclick="validatePasswordForgotten(this.form)"></td>
                </tr>
            </table>
        </form>

        <%@include file="/includes/footer.jsp" %>

