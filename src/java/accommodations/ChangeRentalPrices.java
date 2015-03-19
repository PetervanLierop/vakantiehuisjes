/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accommodations;

import business.Accommodation;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.CurrencyEnum;

/**
 *
 * @author Peter
 */
@WebServlet(name = "ChangeRentalPrices", urlPatterns = {"/ChangeRentalPrices"})
public class ChangeRentalPrices extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    HttpSession session;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Accommodation currentAccommodation =
                (Accommodation) request.getSession().getAttribute("currentAccommodation");
        String message = " ";
        String url;
        String priceLevelDescription;
        String userRoleDescription;
        session = request.getSession(true);
        Enumeration paramNames = request.getParameterNames();
        String paramName;
        while (paramNames.hasMoreElements()) {
            paramName = (String) paramNames.nextElement();
            if (paramName != null) {
                if (paramName.contains("delete")) {
                    priceLevelDescription = paramName.substring(6);
                    currentAccommodation.removePriceLevel(priceLevelDescription);
                    message = "Tarief verwijderd";
                } else if (paramName.contains("selectedCurrency")) {
                    int index = paramName.indexOf("*");
                    userRoleDescription = paramName.substring(16, index);
                    priceLevelDescription = paramName.substring(index + 1);
                    CurrencyEnum priceCurrency = CurrencyEnum.valueOf(request.getParameter(paramName));
                    currentAccommodation.updateRentalRateCurrency(
                            userRoleDescription,
                            priceLevelDescription,
                            priceCurrency);
                    message = "Tarief aangepast";
                } else if (paramName.contains("amount")) {
                    int index = paramName.indexOf("*");
                    userRoleDescription = paramName.substring(6, index);
                    priceLevelDescription = paramName.substring(index + 1);
                    String priceAmountString = request.getParameter(paramName);
                    BigDecimal priceAmount = new BigDecimal(priceAmountString);
                    currentAccommodation.updateRentalRateAmount(
                            userRoleDescription,
                            priceLevelDescription,
                            priceAmount);
                    message = "Tarief aangepast";
                }
            }
        }
        url = "/RentalPrices";
        request.setAttribute("message", message);
        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
