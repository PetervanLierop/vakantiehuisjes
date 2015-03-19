 <%-- 
    Document   : addUser
    Created on : 6-sep-2013, 10:12:14
    Author     : Peter
--%>

<%@ include file="/includes/header.html" %>

<form action="ProcessAddUser" method="post">    
    <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
        <TR>
            <TH>inlogNaam</TH>
            <TD><input type="text" name="inlogName"></TD>
        </TR>
        <TR>
            <TH>Voornaam</TH>
            <TD><input type="text" name="firstName" value="${firstName}"></TD>
        </TR>
        <TR>
            <TH>Achternaam</TH>
            <TD><input type="text" name="lastName" value="${lastName}"></TD>
        </TR>
        <TR>
            <TH>e-mailadres</TH>
            <TD><input type="text" style="width:300px;" name="emailAddress" value="${emailAddress}"></TD>
        </TR>
        <TR>
            <TH>Gebruikersrol</TH>
            <TD>
                <select name="userRoleId">
                    <c:forEach var="userRole" items="${userRoles}">
                        <option value="${userRole.id}"
                                <c:if test="${userRole.description == user.userRole.description}">
                                    selected
                                </c:if>>${userRole.description}
                        </c:forEach>
                </select>
            </TD>
        </TR>
        <TR>
            <TH>Administrator</TH>
            <TD align="left"><input type="checkbox" style="width:300px;" name="administrator" value="${security.administrator}"></TD>
        </TR>
        <TR>
            <TH></TH>
            <TD><BR><input type="button" value="verstuur"
                           onclick="validateAddUser(this.form)"></TD>
        </TR>
    </TABLE>
</form>
<%@include file="/includes/footer.jsp" %>
