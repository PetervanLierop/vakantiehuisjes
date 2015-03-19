<%-- 
    Document   : updateBooking
    Created on : 15-mei-2013, 10:01:19
    Author     : Peter
--%>
<%@ include file="/includes/header.html" %>
<%@include file="/includes/calenderFunctions.html" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="main">
<h2>Boeking aanpassen ${currentAccommodation.name}</h2>
<form action="ProcessUpdateBooking" method="post">
<TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
    <TR>
        <TH>Huurder</TH>
        <TD><input type="text" name="renterUserName" VALUE="${booking.renter.firstName} ${booking.renter.lastName}"></TD>
    </TR>
    <TR>
        <TH>Startdatum</TH>
        <TD><input type="text"  name="startDate" id="txtStartDate" value="<fmt:formatDate type='date' value='${booking.startDate}'/>"></TD>
    </TR>
    <TR>
        <TH>Eindatum</TH>
        <TD><input type="text"  name="endDate" id="txtEndDate" value="<fmt:formatDate type='date' value='${booking.endDate}'/>"></TD>
    </TR>
    <TR>
        <TH><input type="hidden" name="bookingIndex" value="${booking.id}" /></TH>
        <TD><BR><input type="submit" value="verstuur"></TD>
    </TR>
</TABLE>
</form>
    </div>
<%@include file="/includes/footer.jsp" %>
