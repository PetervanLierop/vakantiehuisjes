<%-- 
    Document   : changePassword
    Created on : 11-jul-2013, 15:30:33
    Author     : Peter
--%>

<%@include file="/includes/header.html" %>

<div class="main">
    <h2>Aanpassen van uw wachtwoord.</h2>
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
</div>
<%@include file="/includes/footer.jsp" %>
