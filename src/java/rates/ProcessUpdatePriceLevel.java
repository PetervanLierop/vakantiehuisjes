/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rates;

import business.Accommodation;
import business.PriceLevel;
import data.PriceLevelDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.Check;

/**
 *
 * @author Peter
 */
@WebServlet(name = "ProcessUpdatePriceLevel", urlPatterns = {"/ProcessUpdatePriceLevel"})
public class ProcessUpdatePriceLevel extends HttpServlet {

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
        String message = " ";
        String url;
        boolean inputIsOK = true;
        boolean updateDone = false;
        String priceLevelDescription = request.getParameter("priceLevelDescription");
        String startWeekNumberString = request.getParameter("startWeekNumber");
        String endWeekNumberString = request.getParameter("endWeekNumber");
        String priorityString = request.getParameter("priority");
        String priceLevelIdString = request.getParameter("priceLevelId");
        int priceLevelId = Integer.parseInt(priceLevelIdString);
        PriceLevel priceLevel = PriceLevelDB.selectPriceLevel(priceLevelId);
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
        if (!priceLevelDescription.equals(priceLevel.getDescription())) {
            if (PriceLevelDB.priceLevelDescriptionAlreadyExists(priceLevelDescription)) {
                message = "Tariefnaam is al in gebruik, kies een andere.";
                request.setAttribute("rateDescription", priceLevelDescription);
                request.setAttribute("startWeekNumber", startWeekNumberString);
                request.setAttribute("endWeekNumber", endWeekNumberString);
                request.setAttribute("priority", priorityString);
                inputIsOK = false;
            } else {
                priceLevel.setDescription(priceLevelDescription);
                updateDone = true;
            }
        }
        if (inputIsOK
                && priority != priceLevel.getPriority()) {
            if (PriceLevelDB.priceLevelPriorityAlreadyExists(priority)) {
                message = "Prioriteit is al in gebruik, kies een andere.";
                request.setAttribute("rateDescription", priceLevelDescription);
                request.setAttribute("startWeekNumber", startWeekNumberString);
                request.setAttribute("endWeekNumber", endWeekNumberString);
                request.setAttribute("priority", priorityString);
                inputIsOK = false;
            } else {
                priceLevel.setPriority(priority);
                updateDone = true;
            }
        }
        if (inputIsOK
                && startWeekNumber != priceLevel.getStartWeekNumber()) {
            priceLevel.setStartWeekNumber(startWeekNumber);
            updateDone = true;
        }
        if (inputIsOK
                && endWeekNumber != priceLevel.getEndWeekNumber()) {
            priceLevel.setEndWeekNumber(endWeekNumber);
            updateDone = true;
        }
        if (updateDone) {
            currentAccommodation.setPriceLevel(priceLevel);
            message = "Tarief aangepast";
        }
        if (inputIsOK) {
            url = "/CollectPriceLevels";
        } else {
            request.setAttribute("priceLevel", priceLevel);
            url = "/updatePriceLevel.jsp";
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
