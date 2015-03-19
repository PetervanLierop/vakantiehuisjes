<%-- 
    Document   : addPriceLevelToAccommodation
    Created on : 19-sep-2013, 14:48:52
    Author     : Peter
--%>

<%@include file="/includes/header.html" %>
<div class="main">
    <h1>Tarief toevoegen voor ${currentAccommodation.name}</h1>
    <form action="ProcessAddPriceLevelToAccommodation" method="post">
        <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
            <TR>
                <TH>Gebruikersrol</TH>
                <TH colspan="2">
                    <select name="priceLevel">
                        <c:forEach var="priceLevel" items="${toSelectPriceLevelDescriptions}">
                            <option value="${priceLevel}">
                                ${priceLevel}
                            </c:forEach>
                    </select>
                </TH>
            </TR>
            <c:forEach var="rates" items="${priceLevels}">
                <TR>
                    <TD>${rates.key}</TD>
                    <TD>
                        <select name="selectedCurrency${rates.key}">
                            <c:forEach items="${currencyList}" var="option">
                                <option value="${option}"
                                        <c:if test="${option == price.currency}">
                                            selected
                                        </c:if>>
                                    ${option}
                                </option>
                            </c:forEach>
                        </select>

                    </TD>
                    <TD><input type="text"  name="amount${rates.key}" value="${price.amount}"/></TD>
                </TR>
            </c:forEach>
            <TR>
                <TH><input type="hidden" name="priceLevel" value="${priceLevel}" /></TH>
                <TD colspan="2"><input type="submit" value="verstuur" align="center"></TD>
            </TR>
        </TABLE>
    </form>
</div>
<%@include file="/includes/footer.jsp" %>
