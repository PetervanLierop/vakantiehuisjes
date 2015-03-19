/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accommodations;

import business.Accommodation;
import business.PeriodPrice;
import business.RentalRates;
import data.PriceDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
@WebServlet(name = "RentalPrices", urlPatterns = {"/RentalPrices"})
public class RentalPrices extends HttpServlet {

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
        session = request.getSession(true);
        Accommodation currentAccommodation = (Accommodation) session.getAttribute("currentAccommodation");
        List<String> allPriceLevelDescriptions = currentAccommodation.getAllPriceLevelDescriptions();
        Map<String, RentalRates> priceLevels = currentAccommodation.getPriceLevels();
        List<CurrencyEnum> currencyList = new ArrayList<CurrencyEnum>(Arrays.asList(CurrencyEnum.values()));
        request.setAttribute("currencyList", currencyList);
        request.setAttribute("allPriceLevelDescriptions", allPriceLevelDescriptions);
        request.setAttribute("priceLevels", priceLevels);
        String url = "/rentalPrices.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
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