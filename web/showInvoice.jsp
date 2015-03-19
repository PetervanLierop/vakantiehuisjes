<%-- 
    Document   : showInvoice
    Created on : 7-okt-2013, 14:22:26
    Author     : Peter
--%>

<%@ include file="/includes/header.html" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date"/>

<div class="main">
    <h2>Rekening ${currentUser.firstName} ${currentUser.lastName}</h2>
    <form action="ProcessShowBookings" method="post">
        <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
            <TR>
                <TH>Accommodatie</TH>
                <TH>Startdatum</TH>
                <TH>Eindatum</TH>
                <TH>Prijs</TH>
                <TH>Electriciteit</TH>
                <TH>Totaal</TH>
                <TH>Voldaan</TH>
            </TR>
            <c:forEach var="invoiceLine" items="${invoice.invoiceLines}">
                <TR>
                    <TD>
                        ${invoiceLine.accommodation.name}      
                    </TD>
                    <TD><fmt:formatDate type="date" value="${invoiceLine.startDate}"/></TD>
                    <TD><fmt:formatDate type="date" value="${invoiceLine.endDate}"/></TD>
                    <TD>${invoiceLine.priceAtBookingDate.currency} ${invoiceLine.priceAtBookingDate.amount}</TD>
                    <TD>${invoiceLine.electicityPrice.currency} ${invoiceLine.electicityPrice.amount}</TD>
                    <TD>${invoiceLine.totalPrice.currency} ${invoiceLine.totalPrice.amount}</TD>
                    <TD>
                        <c:if test="${invoiceLine.accountIsMet == true}">
                            Ja
                        </c:if>
                        <c:if test="${invoiceLine.accountIsMet == false}">
                            Nee
                        </c:if>
                    </TD>
                </TR>
            </c:forEach>
            <TR>
                <TD>Totaal: </TD>
                <TD></TD>
                <TD></TD>
                <TD>${invoice.totalRentalPrice.currency} ${invoice.totalRentalPrice.amount}</TD>
                <TD>${invoice.electicityPrice.currency} ${invoice.electicityPrice.amount}</TD>
                <TD>${invoice.totalPrice.currency} ${invoice.totalPrice.amount}</TD>
                <TD></TD>
            </TR>
        </TABLE>
    </form> 
</div>
<%@include file="/includes/footer.jsp" %>
