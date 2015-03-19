<%-- 
    Document   : showPriceLevelsAccommodation
    Created on : 23-sep-2013, 12:19:58
    Author     : Peter
--%>

<%@ include file="/includes/header.html" %>

<div id="main">
    <h1>Verwijder tarief voor ${currentAccommodation.name}</h1>
    <form action="DeletePriceLevelAccommodation" method="post">
        <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
            <TR>
                <TH rowspan="2" height="60">Tarief</TH>
                <TH colspan="2" height="30">Periode</TH>
                <TH rowspan="2" height="40">Prioriteit</TH>
                <TH rowspan="2" height="40" style="width:80px"></TH>
            </TR>
            <TR>
                <TH height="30">Startweeknummer</TH>
                <TH height="30">Eindweeknummer</TH>
            </TR>
            <c:forEach var="priceLevel" items="${priceLevels}">
                <TR>
                    <TD>${priceLevel.description}</TD>
                    <TD>${priceLevel.startWeekNumber}</TD>
                    <TD>${priceLevel.endWeekNumber}</TD>
                    <TD>${priceLevel.priority}</TD>
                    <TD>
                        <c:if test="${priceLevel.id != 1}">
                            <input type="submit" name="delete${priceLevel.description}" value="verwijder">
                        </c:if>
                    </TD>
                </TR>
            </c:forEach>
        </TABLE>    
    </form> 
</div>
<%@include file="/includes/footer.jsp" %>
