<%-- 
    Document   : bookingDetails
    Created on : 17-mei-2013, 12:06:26
    Author     : Peter
--%>
<%@ include file="/includes/header.html" %>
<%@ include file="/includes/calenderFunctions.html" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="main">
    <h2>Electiciteitsverbruik doorgeven voor ${currentAccommodation.name}</h2>
    <h3>Booking periode: 
        <fmt:formatDate type='date' value='${booking.startDate}'/> tot en met 
        <fmt:formatDate type='date' value='${booking.endDate}'/></h3>
    <!--<h2 STYLE="color: #00FF00;">${booking.renter.firstName} ${booking.renter.lastName}</h2>-->
    <h2>Gebooked door: <font color=blue>${booking.renter.firstName} ${booking.renter.lastName}</font></h2>
    <form action="ProcessBookingDetails" method="post">    

        <table  BORDER="1" CELLPADDING="3" CELLSPACING="1">
            <TR>
                <TH align="left">Laag tarief</TH>
                <TD>
                    <table>
                        <TR>
                            <TH align="left">Beginstand</TH>
                            <TD><input type="text"  name="startElectrReadingLow" value="${booking.startElectrReadingLow}"></TD>
                        </TR>
                        <TR>
                            <TH align="left">Eindstand</TH>
                            <TD><input type="text"  name="endElectrReadingLow" value="${booking.endElectrReadingLow}"></TD>
                        </TR>
                    </table>
            </TR>
            <TR>
                <TH align="left">Hoog tarief</TH>
                <TD>
                    <table>
                        <TR>
                            <TH align="left">Beginstand</TH>
                            <TD><input type="text"  name="startElectrReadingHigh" value="${booking.startElectrReadingHigh}"></TD>
                        </TR>
                        <TR>
                            <TH align="left">Eindstand</TH>
                            <TD><input type="text"  name="endElectrReadingHigh" value="${booking.endElectrReadingHigh}"></TD>
                        </TR>
                    </table>
                </TD>
            </TR>
            <TR>
                <TD>
                    <input type="button" value="verstuur"
                           onclick="validateAddBookingDetails(this.form)">
                </TD>
                <TD><input type="hidden" name="bookingIndex" value="${booking.id}" /></TD>

            </TR>
        </table>
    </form>
</div>
<%@include file="/includes/footer.jsp" %>
