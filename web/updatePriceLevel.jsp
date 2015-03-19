<%-- 
    Document   : updatePriceLevel
    Created on : 23-sep-2013, 14:48:35
    Author     : Peter
--%>

<%@ include file="/includes/header.html" %>
<h1>Tarief aanpassen</h1>
<form action="ProcessUpdatePriceLevel" method="post">    
    <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
        <TR>
            <TH>Tariefnaam</TH>
            <TD><input type="text" name="priceLevelDescription" value="${priceLevel.description}"></TD>
        </TR>
        <TR>
            <TH>Startweeknummer</TH>
            <TD><input type="text" name="startWeekNumber" value="${priceLevel.startWeekNumber}"></TD>
        </TR>
        <TR>
            <TH>Eindweeknummer</TH>
            <TD><input type="text" name="endWeekNumber" value="${priceLevel.endWeekNumber}"></TD>
        </TR>
        <TR>
            <TH>Prioriteir</TH>
            <TD><input type="text" name="priority" value="${priceLevel.priority}"></TD>
        </TR>

        <TR>
            <TH><input type="hidden" name="priceLevelId" value="${priceLevel.id}" /></TH>
            <TD><BR>
                <!--<input type="submit" value="verstuur"/>-->
                <input type="button" value="verstuur" onclick="validatePriceLevel(this.form)">
            </TD>
        </TR>
    </TABLE>
</form>
<%@include file="/includes/footer.jsp" %>
