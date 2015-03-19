<%-- 
    Document   : showBookings
    Created on : 25-apr-2013, 13:07:35
    Author     : Peter
--%>
<%@ include file="/includes/header.html" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date"/>

<div class="main">
<h2>Boeking overzicht ${currentAccommodation.name}</h2>
<form action="ProcessShowBookings" method="post">
<TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
    <TR>
        <TH>Huurder</TH>
        <TH>Startdatum</TH>
        <TH>Eindatum</TH>
        <TH></TH>
        <TH></TH>
        <TH></TH>
    </TR>
    <c:forEach var="booking" items="${bookings}">
    <TR>
        <TD>
            <c:choose>
                <c:when test="${isAdministrator or booking.renter.id == currentUser.id}">
                    ${booking.renter.firstName} ${booking.renter.lastName}
                </c:when>
                <c:otherwise>
                    **********
                </c:otherwise>
            </c:choose>        
        </TD>
        <TD><fmt:formatDate type="date" value="${booking.startDate}"/></TD>
        <TD><fmt:formatDate type="date" value="${booking.endDate}"/></TD>
        <TD><input type="submit" name="detail${booking.id}" value="meterstanden" 
            <c:if test="${not(isAdministrator or booking.renter.id == currentUser.id) or booking.startDate gt now}">
                    disabled
            </c:if>>
        </TD>
        <TD><input type="submit" name="update${booking.id}" value="aanpassen" 
            <c:if test="${not(isAdministrator or booking.renter.id == currentUser.id)}">
                    disabled
            </c:if>>
        </TD>
        <TD><input type="submit" name="delete${booking.id}" value="verwijder" 
            <c:if test="${not(isAdministrator or booking.renter.id == currentUser.id) or booking.endDate lt now}">
                    disabled
            </c:if>>
        </TD>
    </TR>
    </c:forEach>
</TABLE>
</form> 
</div>
<%@include file="/includes/footer.jsp" %>
