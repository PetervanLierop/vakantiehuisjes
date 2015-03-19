<%-- 
    Document   : addUserRole
    Created on : 5-sep-2013, 14:11:48
    Author     : Peter
--%>

<%@include file="/includes/header.html" %>
<div class="main">
    <h2>Gebruikersrol toevoegen</h2>
    <form action="ProcessAddUserRole" method="post">    
        <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
            <TR>
                <TH>Gebruikersrol</TH>
                <TD><input type="text" name="userRoleDescription"></TD>
            </TR>
        </TABLE>
        <p><i>De bijbehorende tarieven van de accommodaties moeten ook worden ingevuld.</i></p>
        <TABLE  BORDER="1" CELLPADDING="3" CELLSPACING="1" title="Standaardtarief">
            <c:forEach items="${accommdationPriceLevels}" var="accommdationPriceLevel">
                <TBODY>
                    <TR><TH colspan="2">Accommodatie</TH>
                        <c:forEach items="${accommdationPriceLevel.value}" var="priceLevel">
                            <TH colspan="2">${priceLevel.description}</TH>
                        </c:forEach>
                    </TR>
                    <TR>
                        <TH rowspan="2" height="80">${accommdationPriceLevel.key}</TH>
                        <TD rowspan="2" height="80" style="text-align: center;">
                            <img src="images/${accommdationPriceLevel.key}.gif" width="120" height="80" />
                        </TD>
                        <c:forEach items="${accommdationPriceLevel.value}" var="priceLevel">
                            <TH height="40">Valuta</TH>
                            <TD height="40">
                                <select name="selectedCurrency${accommdationPriceLevel.key}*${priceLevel.description}">
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
                        </c:forEach>
                    </TR>
                    <TR>
                        <c:forEach items="${accommdationPriceLevel.value}" var="priceLevel">
                            <TH height="40">Tarief per week</TH>
                            <TD height="40"><input type="text"  name="amount${accommdationPriceLevel.key}*${priceLevel.description}" value="${price.amount}"/></TD>
                            </c:forEach>
                    </TR>

                </TBODY>
            </c:forEach>
            <TR>
                <TD colspan="4"><input type="submit" value="verstuur"></TD>
            </TR>
        </TABLE>


    </form>
</div>
<%@include file="/includes/footer.jsp" %>
