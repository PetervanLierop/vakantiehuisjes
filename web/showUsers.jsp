<%-- 
    Document   : showUsers
    Created on : 30-jul-2013, 16:31:13
    Author     : Peter
--%>

<%@ include file="/includes/header.html" %>

<div id="main">
    <h2>Alle gebruikers</h2>
    <form action="ProcessShowUsers" method="post">
        <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
            <TR>
                <TH>Gebruiker</TH>
                <TH>Gebruikers rol</TH>
                <TH>Administrator</TH>
                <TH></TH>
                <TH></TH>
            </TR>
            <c:forEach var="user" items="${users}">
                <TR>
                    <TD>${user.firstName} ${user.lastName}</TD>
                    <TD>${user.userRole.description}</TD>
                    <TD>
                        <c:if test="${user.administrator == true}">
                            Ja
                        </c:if>
                        <c:if test="${user.administrator == false}">
                            Nee
                        </c:if>
                    </TD>
                    <TD>
                        <c:if test="${user.id != currentUser.id}">
                            <input type="submit" name="update${user.id}" value="aanpassen">
                        </c:if>
                    </TD>
                    <TD>
                        <c:if test="${user.id != currentUser.id}">
                            <input type="submit" name="delete${user.id}" value="verwijder">
                        </c:if>
                    </TD>
                </TR>
            </c:forEach>
        </TABLE>
    </form> 
</div>
<%@include file="/includes/footer.jsp" %>
