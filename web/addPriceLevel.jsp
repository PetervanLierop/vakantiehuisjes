<%-- 
    Document   : addPriceLevel
    Created on : 19-sep-2013, 12:33:24
    Author     : Peter
--%>

<%@ include file="/includes/header.html" %>
<h1>Tarief toevoegen</h1>
<form action="ProcessAddPriceLevel" method="post">    
    <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
        <TR>
            <TH>Tariefnaam</TH>
            <TD><input type="text" name="priceLevelDescription" value="${priceLevelDescription}"></TD>
        </TR>
        <TR>
            <TH>Startweeknummer</TH>
            <TD><input type="text" name="startWeekNumber" value="${startWeekNumber}"></TD>
        </TR>
        <TR>
            <TH>Eindweeknummer</TH>
            <TD><input type="text" name="endWeekNumber" value="${endWeekNumber}"></TD>
        </TR>
        <TR>
            <TH>Prioriteir</TH>
            <TD><input type="text" name="priority" value="${priority}"></TD>
        </TR>

        <TR>
            <TH></TH>
            <TD><BR><input type="button" value="verstuur"
                           onclick="validatePriceLevel(this.form)"></TD>
        </TR>
    </TABLE>
</form>
<%@include file="/includes/footer.jsp" %>
