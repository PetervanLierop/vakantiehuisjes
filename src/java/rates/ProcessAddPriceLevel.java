/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rates;

import business.PriceLevel;
import data.PriceLevelDB;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.Check;

/**
 *
 * @author Peter
 */
@WebServlet(name = "ProcessAddPriceLevel", urlPatterns = {"/ProcessAddPriceLevel"})
public class ProcessAddPriceLevel extends HttpServlet {

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = " ";
        String url;
        boolean inputIsOK = true;
        String rateDescription = request.getParameter("rateDescription");
        String startWeekNumberString = request.getParameter("startWeekNumber");
        String endWeekNumberString = request.getParameter("endWeekNumber");
        String priorityString = request.getParameter("priority");
        int startWeekNumber = 0;
        if (Check.isInteger(startWeekNumberString)) {
            startWeekNumber = Integer.parseInt(startWeekNumberString); 
        }
        int endWeekNumber = 0;
        if (Check.isInteger(endWeekNumberString)) {
            endWeekNumber = Integer.parseInt(endWeekNumberString); 
        }
        int priority = 0;
        if (Check.isInteger(priorityString)) {
            priority = Integer.parseInt(priorityString); 
        }
        if (PriceLevelDB.priceLevelDescriptionAlreadyExists(rateDescription)) {
            message = "Tariefnaam is al in gebruik, kies een andere.";
            request.setAttribute("startWeekNumber", startWeekNumberString);
            request.setAttribute("endWeekNumber", endWeekNumberString);
            request.setAttribute("priority", priorityString);
            inputIsOK = false;
        }
        if (PriceLevelDB.priceLevelPriorityAlreadyExists(priority)) {
            message = "Prioriteit is al in gebruik, kies een andere.";
            request.setAttribute("rateDescription", rateDescription);
            request.setAttribute("startWeekNumber", startWeekNumberString);
            request.setAttribute("endWeekNumber", endWeekNumberString);
            inputIsOK = false;
        }
        if (inputIsOK) {
            PriceLevel priceLevel = new PriceLevel(rateDescription, startWeekNumber, endWeekNumber, priority);
            priceLevel = PriceLevelDB.insertPriceLevel(priceLevel);
            message = "Tarief toegevoegd";
            url  = "/CollectPriceLevels";
        } else {
            url = "/addPriceLevel.jsp";
        }
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
