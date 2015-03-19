<%-- 
    Document   : addBooking
    Created on : 25-apr-2013, 21:48:25
    Author     : Peter
--%>
<%@include file="/includes/header.html" %>
<%@include file="/includes/calenderFunctions.html" %>

<div class="main">
    <h2>Booking toevoegen ${currentAccommodation.name}</h2>
    <form action="ProcessAddBooking" method="post">    
        <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
            <TR>
                <TH>Huurder</TH>
                <TD>
                    <select name="renterSelection">
                        <c:forEach var="user" items="${selectableUsers}">
                            <option value="${user.id}" 
                                    <c:if test="${currentUser.id == user.id}">
                                        selected
                                    </c:if>/>
                            ${user.firstName} ${user.lastName}
                        </c:forEach>
                    </select>
                </TD>
            </TR>
            <TR>
                <TH>Startdatum</TH>
                <TD><input type="text" id="txtStartDate" name="startDate"></TD>
            </TR>
            <TR>
                <TH>Eindatum</TH>
                <TD><input type="text" id="txtEndDate" name="endDate"></TD>
            </TR>
            <TR>
                <TH></TH>
                <TD><BR><input type="submit" value="verstuur"></TD>
            </TR>
        </TABLE>
    </form>
</div>
<%@include file="/includes/footer.jsp" %>
