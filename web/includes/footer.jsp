<%@ page import="java.util.*" %>
<%
    // initialize the current year that's used in the copyright notice
    GregorianCalendar currentDate = new GregorianCalendar();
    int currentYear = currentDate.get(Calendar.YEAR);
%>
    </body>
    <div class="footer">
            <p><small>
            &copy; Copyright <%= currentYear %> Peter van Lierop
            All rights reserved</small></p>
    </div>
</div>
</html>