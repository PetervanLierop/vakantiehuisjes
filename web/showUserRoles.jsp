<%-- 
    Document   : showUserRoles
    Created on : 30-jul-2013, 17:27:48
    Author     : Peter
--%>

<%@ include file="/includes/header.html" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="main">
<h2>Gebruikersrollen overzicht</h2>
<form action="DeleteUserRole" method="post">
<TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
    <TR>
        <TH>Gebruikersrol</TH>
        <TH></TH>
    </TR>
    <c:forEach var="userRole" items="${userRoles}">
    <TR>
        <TD>${userRole.description}</TD>
        <TD>
           <c:if test="${userRole.id != 1 and userRole.id != 2}">
                <input type="submit" name="delete${userRole.id}" value="verwijder">
           </c:if>
        </TD>
    </TR>
    </c:forEach>
</TABLE>    
</form> 
</div>
<%@include file="/includes/footer.jsp" %>
