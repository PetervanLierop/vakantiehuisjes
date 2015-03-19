/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rates;

import business.Accommodation;
import business.PriceLevel;
import data.PriceLevelDB;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Peter
 */
@WebServlet(name = "ProcessShowPriceLevel", urlPatterns = {"/ProcessShowPriceLevel"})
public class ProcessShowPriceLevel extends HttpServlet {

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
        Enumeration paramNames = request.getParameterNames();
        String paramName;
        String url = "";
        String message = "";
        while (paramNames.hasMoreElements()) {
            paramName = (String) paramNames.nextElement();
            if (paramName != null
                    && paramName.contains("delete")) {
                String priceLevelDescription = paramName.substring(6);
                PriceLevel priceLevel = PriceLevelDB.selectPriceLevel(priceLevelDescription);
                currentAccommodation.removePriceLevel(priceLevel);
                PriceLevelDB.deletePriceLevel(priceLevel);
                message = "Tarief verwijderd";
                url = "/CollectPriceLevels";
            }
            if (paramName.contains("update")) {
                int priceLevelIndex = Integer.parseInt(paramName.substring(6));
                PriceLevel priceLevel = PriceLevelDB.selectPriceLevel(priceLevelIndex);
                request.setAttribute("priceLevel", priceLevel);
                url = "/updatePriceLevel.jsp";
            }
        }
        request.setAttribute("message", message);
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
