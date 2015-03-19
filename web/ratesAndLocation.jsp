<%-- 
    Document   : ratesAndLocation
    Created on : 24-mei-2013, 13:07:35
    Author     : Peter
--%>

<%@include file="/includes/header.html" %>

<div class="main">
<H2>Prijzen ${currentAccommodation.name} (in euro's):</H2>
<form action="notKnownYet" method="post">    
<TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
        <c:forEach items="${periodPrices}" var="periodPrice">
    <TR>
        <TH>${periodPrice.priceLevel.description}</TH>
        <TD>${periodPrice.price.currency} ${periodPrice.price.amount}</TD>
    </TR>
    </c:forEach>
    <TR>
        <TH>Electiciteit (laag tarief) per tik</TH>
        <TD>${powerSupplier.lowRate.currency} ${powerSupplier.lowRate.amount}</TD>
    </TR>
    <TR>
        <TH>Electiciteit (hoog tarief) per tik</TH>
        <TD>${powerSupplier.highRate.currency} ${powerSupplier.highRate.amount}</TD>
    </TR>
    <TR>
        <TH>Schoonmaakkosten per keer</TH>
        <TD>${currentAccommodation.cleaningCosts.currency} ${currentAccommodation.cleaningCosts.amount}</TD>
    </TR>
</TABLE>
</form>
<h2>Locatie ${currentAccommodation.name}:</h2>
<iframe width="425" height="350" scrolling="no" marginheight="0" marginwidth="0" src="https://maps.google.nl/maps?q=${currentAccommodation.gpsLatitude},${currentAccommodation.gpsLongitude}&amp;num=1&amp;t=h&amp;ie=UTF8&amp;z=14&amp;ll=${currentAccommodation.gpsLatitude},${currentAccommodation.gpsLongitude}&amp;output=embed"></iframe><br /><small><a href="https://maps.google.nl/maps?q=${currentAccommodation.gpsLatitude},${currentAccommodation.gpsLongitude}&amp;num=1&amp;t=h&amp;ie=UTF8&amp;z=14&amp;ll=${currentAccommodation.gpsLatitude},${currentAccommodation.gpsLongitude}  &amp;source=embed" style="color:#0000FF;text-align:left">Grotere kaart weergeven</a></small>
</div>

<%@include file="/includes/footer.jsp" %>