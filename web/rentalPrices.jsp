<%-- 
    Document   : rentalPrices
    Created on : 26-aug-2013, 11:21:43
    Author     : Peter
--%>

<%@include file="/includes/header.html" %>
<h1>Tarieven voor ${currentAccommodation.name}</h1>
<form action="ChangeRentalPrices" method="post">
    <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
        <TR>
            <TH>Gebruikersrol</TH>
            <c:forEach var="priceLevelDescription" items="${allPriceLevelDescriptions}">
                <TH colspan="2">${priceLevelDescription}</TH>
            </c:forEach>
        </TR>
        <c:forEach var="rates" items="${priceLevels}">
            <TR>
                <TD>${rates.key}</TD>
                <c:forEach var="periodPrice" items="${rates.value.periodPrices}">
                    <TD>
                        <select name="selectedCurrency${rates.key}*${periodPrice.priceLevel.description}">
                            <c:forEach items="${currencyList}" var="option">
                                <option value="${option}"
                                        <c:if test="${option == periodPrice.price.currency}">
                                            selected
                                        </c:if>>
                                    ${option}
                                </option>
                            </c:forEach>
                        </select>
                    </TD>
                    <TD>
                        <input type="text"  name="amount${rates.key}*${periodPrice.priceLevel.description}" value="${periodPrice.price.amount}"/>
                    </TD>
                </c:forEach>
            </TR>
        </c:forEach>
    </TABLE>
    <BR>    
    <center>
        Prijsaanpassingen
        <input type="submit" name="update" value="opslaan"/>
        <input type="reset" name="Reset" value="Reset"/>
    </center>
</form>
<%@include file="/includes/footer.jsp" %>
