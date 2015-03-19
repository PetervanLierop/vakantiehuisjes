/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userRoles;

import business.Accommodation;
import business.PeriodPrice;
import business.Price;
import business.PriceLevel;
import business.RentalRates;
import business.UserRole;
import data.AccommodationDB;
import data.PriceDB;
import data.UserRoleDB;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
@WebServlet(name = "ProcessAddUserRole", urlPatterns = {"/ProcessAddUserRole"})
public class ProcessAddUserRole extends HttpServlet {

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
        boolean inputIsOK = true;
        String message = " ";
        String url = " ";
        Accommodation accommodation;
        String priceLevelDescription;
        Price price;
        UserRole userRole = null;
        session = request.getSession(true);
        String userRoleDescription = request.getParameter("userRoleDescription");
        if (UserRoleDB.userRoleExists(userRoleDescription)) {
            inputIsOK = false;
            message = "Gebruikersrol bestaat al";
            url = "/AddUserRole";
        } else {
            userRole = new UserRole(userRoleDescription);
            userRole = UserRoleDB.insertUserRole(userRole);
        }
        if (inputIsOK) {
            List<Accommodation> accommodationsList = AccommodationDB.selectAllAccommodations();
            Map<String, Accommodation> accommodationsMap = new LinkedHashMap<String, Accommodation>();
            Iterator<Accommodation> accommodationListIterator = accommodationsList.iterator();
            while (accommodationListIterator.hasNext()) {
                accommodation = accommodationListIterator.next();
                accommodation.setPriceLevels(userRole.getDescription(), new RentalRates(userRoleDescription));
                accommodationsMap.put(accommodation.getName(), accommodation);
            }
            Enumeration paramNames = request.getParameterNames();
            String paramName;
            while (paramNames.hasMoreElements()) {
                paramName = (String) paramNames.nextElement();
                if (paramName != null
                        && paramName.contains("selectedCurrency")) {
                    int index = paramName.indexOf("*");
                    String accommodationName = paramName.substring(16, index);
                    priceLevelDescription = paramName.substring(index + 1);
                    CurrencyEnum priceCurrency = CurrencyEnum.valueOf(request.getParameter(paramName));
                    accommodationsMap.get(accommodationName).getPriceLevels().get(
                            userRole.getDescription()).setPeriodPrices(priceLevelDescription, priceCurrency);
                }
                if (paramName != null
                        && paramName.contains("amount")) {
                    int index = paramName.indexOf("*");
                    String accommodationName = paramName.substring(6, index);
                    priceLevelDescription = paramName.substring(index + 1);
                    String priceAmount = request.getParameter(paramName);
                    accommodationsMap.get(accommodationName).getPriceLevels().get(
                            userRole.getDescription()).setPeriodPrices(priceLevelDescription, new BigDecimal(priceAmount));
                }
            }
            Iterator<Entry<String, Accommodation>> accommodationMapIterator =
                    accommodationsMap.entrySet().iterator();
            while (accommodationMapIterator.hasNext()) {
                Map.Entry accommodationEntry = (Map.Entry) accommodationMapIterator.next();
                accommodation = (Accommodation) accommodationEntry.getValue();
                List<PeriodPrice> periodPrices = accommodation.getPriceLevels().get(userRoleDescription).getPeriodPrices();
                Iterator<PeriodPrice> iterator = periodPrices.iterator();
                while (iterator.hasNext()) {
                    PeriodPrice periodPrice = iterator.next();
                    PriceLevel priceLevel = periodPrice.getPriceLevel();
                    price = periodPrice.getPrice();
                    PriceDB.insertRentalRate(price, userRole, priceLevel, accommodation);
                    if (accommodation.equals(currentAccommodation)) {
                        if (!currentAccommodation.getPriceLevels().containsKey(userRole.getDescription())) {
                            currentAccommodation.setPriceLevels(userRole.getDescription(), new RentalRates(userRole.getDescription()));
                        }
                        currentAccommodation.getPriceLevels().get(userRole.getDescription()).addPeriodPrices(priceLevel, price);
                    }
                }
                message = "Gebruikersrol toegevoegd";
                url = "/ShowUserRoles";
            }

            request.setAttribute(
                    "message", message);
            RequestDispatcher dispatcher =
                    getServletContext().getRequestDispatcher(url);

            dispatcher.forward(request, response);
        }
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
