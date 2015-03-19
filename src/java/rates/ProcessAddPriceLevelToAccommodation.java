/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rates;

import business.Accommodation;
import business.PeriodPrice;
import business.Price;
import business.PriceLevel;
import business.UserRole;
import data.PriceDB;
import data.UserRoleDB;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "ProcessAddPriceLevelToAccommodation", urlPatterns = {"/ProcessAddPriceLevelToAccommodation"})
public class ProcessAddPriceLevelToAccommodation extends HttpServlet {

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
        String message;
        String url;
        Price price;
        PriceLevel priceLevel;
        String priceLevelDescription;
        UserRole userRole = null;
        String userRoleDescription;
        session = request.getSession(true);
        priceLevelDescription = request.getParameter("priceLevel");
        priceLevel = PriceDB.selectPriceLevel(priceLevelDescription);
        Enumeration paramNames = request.getParameterNames();
        String paramName;
        while (paramNames.hasMoreElements()) {
            paramName = (String) paramNames.nextElement();
            if (paramName != null
                    && paramName.contains("selectedCurrency")) {
                userRoleDescription = paramName.substring(16);
                CurrencyEnum priceCurrency = CurrencyEnum.valueOf(request.getParameter(paramName));
                currentAccommodation.getPriceLevels().get(
                        userRoleDescription).setPeriodPrices(priceLevelDescription, priceCurrency);
            }
            if (paramName != null
                    && paramName.contains("amount")) {
                userRoleDescription = paramName.substring(6);
                String priceAmount = request.getParameter(paramName);
                currentAccommodation.getPriceLevels().get(
                        userRoleDescription).setPeriodPrices(priceLevelDescription, new BigDecimal(priceAmount));
            }
        }
        for (String userRoleEntryDescription : currentAccommodation.getPriceLevels().keySet()) {
            userRole = UserRoleDB.selectUserRole(userRoleEntryDescription);
            List<PeriodPrice> periodPrices = currentAccommodation.getPriceLevels().get(userRoleEntryDescription).getPeriodPrices();
            Iterator<PeriodPrice> iterator = periodPrices.iterator();
            while (iterator.hasNext()) {
                PeriodPrice periodPrice = iterator.next();
                if (priceLevel.equals(periodPrice.getPriceLevel())) {
                    price = periodPrice.getPrice();
                    PriceDB.insertRentalRate(price, userRole, priceLevel, currentAccommodation);
                }
            }
        }
        message = "Tarief toegevoegd";
        url = "/rentalPrices";
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
