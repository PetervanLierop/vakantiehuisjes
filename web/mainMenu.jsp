<%-- 
    Document   : mainMenu
    Created on : 18-feb-2013, 16:05:47
    Author     : Peter
--%>
<%@include file="/includes/header.html" %>

<div class="main">
<h2>Selecteer een accommodatie:</h2>
<form action="SelectAccommodation" method="post">
<p>
<select name="selectedAccommodationName" onchange="this.form.submit()">
    <c:forEach var="accommodation" items="${accommodations}">
        <option
            <c:if test="${accommodation.name == currentAccommodation.name}">
                selected
            </c:if>>
                ${accommodation.name}
        </option>
    </c:forEach>
</select>
</p>
</form>
</div>
<%@include file="/includes/footer.jsp" %>
