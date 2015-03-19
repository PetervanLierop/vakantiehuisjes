<%-- 
    Document   : updateUserUserRole
    Created on : 5-sep-2013, 17:41:27
    Author     : Peter
--%>

<%@ include file="/includes/header.html" %>

<div class="main">
    <h2>Gebruikersrol aanpassen van ${user.firstName} ${user.lastName}</h2>

    <form action="UpdateUserRole" method="post">    
        <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
            <TR>
                <TH>Grebruikersrol</TH>
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
                <TD><input type="checkbox" name="administrator"
                           <c:if test="${user.administrator == true}">
                               checked="checked"
                           </c:if>></TD>
            </TR>
            <TR>
                <TH><input type="hidden" name="userId" value="${user.id}" /></TH>
                <TD><BR><input type="submit" value="opslaan"></TD>
            </TR>
        </TABLE>
    </form>
    <%@include file="/includes/footer.jsp" %>

