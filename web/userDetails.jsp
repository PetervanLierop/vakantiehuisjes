<%-- 
    Document   : userDetails
    Created on : 15-jul-2013, 11:18:27
    Author     : Peter
--%>

<%@ include file="/includes/header.html" %>

<form action="ProcessUpdateUser" method="post">    
<TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
     <TR>
        <TH>inlogName</TH>
        <TD><input type="text" name="inlogName" value="${currentUser.inlogName}"></TD>
    </TR>
    <TR>
        <TH>Voornaam</TH>
        <TD><input type="text" name="firstName" value="${currentUser.firstName}"></TD>
    </TR>
    <TR>
        <TH>Achternaam</TH>
        <TD><input type="text" name="lastName" value="${currentUser.lastName}"></TD>
    </TR>
    <TR>
        <TH>e-mailadres</TH>
        <TD><input type="text" style="width:300px;" name="emailAddress" value="${currentUser.emailAddress}"></TD>
    </TR>
    <TR>
        <TH><input type="hidden" name="currentUserIndex" value="${currentUser.id}" /></TH>
        <TD><BR><input type="submit" value="opslaan"></TD>
    </TR>
</TABLE>
</form>
<%@include file="/includes/footer.jsp" %>
